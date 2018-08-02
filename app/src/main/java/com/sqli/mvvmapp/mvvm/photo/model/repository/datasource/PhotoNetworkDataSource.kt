package com.sqli.mvvmapp.mvvm.photo.model.repository.datasource

import com.sqli.mvvmapp.common.network.NetworkService
import com.sqli.mvvmapp.mvvm.photo.model.entity.Photo
import io.reactivex.Single
import dagger.Lazy

class PhotoNetworkDataSource(val networkService: Lazy<NetworkService>) : PhotoDataSource {
    override fun getPhotos(albumId: Long): Single<List<Photo>>
            = networkService.get().getPhotos(albumId)

    override fun getPhoto(photoId: Long): Single<Photo>
            = networkService.get().getPhoto(photoId)


}