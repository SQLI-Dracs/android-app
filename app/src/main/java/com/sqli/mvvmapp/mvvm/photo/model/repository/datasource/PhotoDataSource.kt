package com.sqli.mvvmapp.mvvm.photo.model.repository.datasource

import com.sqli.mvvmapp.mvvm.album.model.entity.Album
import com.sqli.mvvmapp.mvvm.photo.model.entity.Photo
import io.reactivex.Single

interface PhotoDataSource {

    fun getPhotos(albumId: Long): Single<List<Photo>>

    fun getPhoto(photoId: Long): Single<Photo>

}