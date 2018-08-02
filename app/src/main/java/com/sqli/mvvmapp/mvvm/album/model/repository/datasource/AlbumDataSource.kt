package com.sqli.mvvmapp.mvvm.album.model.repository.datasource

import com.sqli.mvvmapp.mvvm.album.model.entity.Album

import io.reactivex.Single

interface AlbumDataSource {

    fun getAlbums(userId: Long): Single<List<Album>>
}
