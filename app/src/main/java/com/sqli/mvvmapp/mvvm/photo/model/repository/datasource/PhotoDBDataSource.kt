package com.sqli.mvvmapp.mvvm.photo.model.repository.datasource

import com.sqli.mvvmapp.common.cache.DBAdapter
import com.sqli.mvvmapp.mvvm.photo.model.db.PhotoEntity
import com.sqli.mvvmapp.mvvm.photo.model.entity.Photo
import io.reactivex.Single
import dagger.Lazy
import io.reactivex.Completable

class PhotoDBDataSource(val dbAdapter: Lazy<DBAdapter>) : PhotoDataSource{

    override fun getPhoto(photoId: Long): Single<Photo> {
        return dbAdapter.get().getPhoto(photoId).map {
            Photo(it.albumId, it.id, it.title, it.url, it.thumbnailUrl)
        }
    }

    override fun getPhotos(albumId: Long): Single<List<Photo>> {

        return dbAdapter.get().getPhotos(albumId)
                .flattenAsObservable { photos -> photos }
                .map { Photo(it.albumId, it.id, it.title, it.url, it.thumbnailUrl) }
                .toList()

    }

    fun insert(photoEntity: PhotoEntity): Completable = dbAdapter.get().insert(photoEntity)

    fun delete(photoEntity: PhotoEntity): Completable = dbAdapter.get().delete(photoEntity)


}