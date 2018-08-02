package com.sqli.mvvmapp.mvvm.album.model.repository.data;

import com.sqli.mvvmapp.mvvm.album.model.db.AlbumEntity;
import com.sqli.mvvmapp.mvvm.album.model.entity.Album;
import com.sqli.mvvmapp.mvvm.album.model.mapper.AlbumMapper;
import com.sqli.mvvmapp.mvvm.album.model.repository.datasource.AlbumDBDataSource;

import java.util.List;
import java.util.concurrent.Callable;

import javax.inject.Inject;

import dagger.Lazy;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.SingleSource;

public class AlbumDataRepository implements AlbumRepository {

    private Lazy<AlbumMapper> albumMapper;
    private Lazy<AlbumDataFactory> albumDataFactory;

    @Inject
    public AlbumDataRepository(Lazy<AlbumMapper> albumMapper, Lazy<AlbumDataFactory> albumDataFactory) {
        this.albumMapper = albumMapper;
        this.albumDataFactory = albumDataFactory;
    }

    @Override
    public Observable<List<Album>> getAlbums(long userId) {

        AlbumDBDataSource albumDBDataSource = albumDataFactory.get().createDBDataSource();

        Single<List<Album>> dbSingle = albumDBDataSource.getAlbums(userId);
        Single<List<Album>> nwSingle = albumDataFactory.get().createNetworkDataSource().getAlbums(userId)
                .flattenAsObservable(albums -> albums)
                .flatMapSingle(album -> {
                    AlbumEntity albumEntity = albumMapper.get().transform(album);
                    return albumDBDataSource.delete(albumEntity)
                            .andThen(albumDBDataSource.insert(albumEntity)).andThen(Single.just(album));
                })
                .toList();

        return Observable.merge(dbSingle.toObservable(), nwSingle.toObservable());
    }
}
