package com.sqli.mvvmapp.mvvm.photo.model.mapper

import com.sqli.mvvmapp.mvvm.photo.model.db.PhotoEntity
import com.sqli.mvvmapp.mvvm.photo.model.entity.Photo
import javax.inject.Inject

class PhotoMapper @Inject constructor() {
    fun transform(photo: Photo) : PhotoEntity = PhotoEntity(photo.id, photo.albumId, photo.title, photo.url, photo.thumbnailUrl)
}