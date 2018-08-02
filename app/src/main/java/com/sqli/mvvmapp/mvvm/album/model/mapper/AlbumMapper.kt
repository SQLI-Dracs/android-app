package com.sqli.mvvmapp.mvvm.album.model.mapper

import com.sqli.mvvmapp.mvvm.album.model.db.AlbumEntity
import com.sqli.mvvmapp.mvvm.album.model.entity.Album
import javax.inject.Inject

class AlbumMapper @Inject constructor() {
    fun transform(album: Album) : AlbumEntity = AlbumEntity(album.userId, album.id, album.title)
}