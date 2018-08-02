package com.sqli.mvvmapp.mvvm.album.model.repository.data;

import com.sqli.mvvmapp.mvvm.album.model.entity.Album;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;

public interface AlbumRepository {

    Observable<List<Album>> getAlbums(long userId);

}
