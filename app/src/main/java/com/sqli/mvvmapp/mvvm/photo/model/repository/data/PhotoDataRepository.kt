package com.sqli.mvvmapp.mvvm.photo.model.repository.data

import com.sqli.mvvmapp.mvvm.photo.model.entity.Photo
import com.sqli.mvvmapp.mvvm.photo.model.mapper.PhotoMapper
import com.sqli.mvvmapp.mvvm.photo.model.repository.datasource.PhotoDBDataSource
import com.sqli.mvvmapp.mvvm.photo.model.repository.datasource.PhotoNetworkDataSource
import dagger.Lazy
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject

class PhotoDataRepository @Inject
constructor(val photoMapper: Lazy<PhotoMapper>, val photoDataFactory: Lazy<PhotoDataFactory>) : PhotoRepository {


    override fun getPhotos(albumId: Long): Observable<List<Photo>> {
        val photoDBDataSource: PhotoDBDataSource = photoDataFactory.get().createDBDataSource()
        val photoNetworkDataSource: PhotoNetworkDataSource = photoDataFactory.get().createNetworkDataSource()

        var dbSingle = photoDBDataSource.getPhotos(albumId)

        var nwSingle: Single<List<Photo>> = photoNetworkDataSource.getPhotos(albumId)
                .flattenAsObservable { photos -> photos }
                .flatMapSingle { photo ->
                    val photoEntity = photoMapper.get().transform(photo)

                    photoDBDataSource.delete(photoEntity)
                            .andThen(photoDBDataSource.insert(photoEntity))
                            .andThen(Single.just(photo))

                }.toList()

        return Observable.merge(dbSingle.toObservable(), nwSingle.toObservable())

    }

    override fun getPhoto(photoId: Long): Observable<Photo> {
        val photoDBDataSource: PhotoDBDataSource = photoDataFactory.get().createDBDataSource()
        val photoNetworkDataSource: PhotoNetworkDataSource = photoDataFactory.get().createNetworkDataSource()

        val dbSingle = photoDBDataSource.getPhoto(photoId)
        val nwSingle = photoNetworkDataSource.getPhoto(photoId).flatMap {
            val photoEntity = photoMapper.get().transform(it)

            photoDBDataSource.delete(photoEntity)
                    .andThen(photoDBDataSource.insert(photoEntity))
                    .andThen(Single.just(it))
        }

        return Observable.merge(dbSingle.toObservable(), nwSingle.toObservable())
    }

}