package com.sqli.mvvmapp.mvvm.photo.model.repository.data

import com.sqli.mvvmapp.mvvm.photo.model.entity.Photo
import io.reactivex.Observable

interface PhotoRepository {
    fun getPhotos(albumId: Long): Observable<List<Photo>>
    fun getPhoto(photoId: Long): Observable<Photo>
}