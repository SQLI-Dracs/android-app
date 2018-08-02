package com.sqli.mvvmapp.mvvm.album.model.repository.datasource

import com.sqli.mvvmapp.common.cache.DBAdapter
import com.sqli.mvvmapp.mvvm.album.model.db.AlbumEntity
import com.sqli.mvvmapp.mvvm.album.model.entity.Album
import dagger.Lazy
import io.reactivex.Completable
import io.reactivex.Single

class AlbumDBDataSource(val dbAdapter: Lazy<DBAdapter>) : AlbumDataSource {

    override fun getAlbums(userId: Long): Single<List<Album>> {

        return dbAdapter.get().getAlbums(userId)
                .flattenAsObservable { albumEntities -> albumEntities }
                .map { albumEntity: AlbumEntity -> Album(albumEntity.userId, albumEntity.userId, albumEntity.title) }
                .toList()
    }


    fun insert(albumEntity: AlbumEntity): Completable = dbAdapter.get().insert(albumEntity)

    fun delete(albumEntity: AlbumEntity): Completable = dbAdapter.get().delete(albumEntity)
}
