package com.sqli.mvvmapp.mvvm.album.model.repository.datasource

import com.sqli.mvvmapp.common.network.NetworkService
import com.sqli.mvvmapp.mvvm.album.model.entity.Album
import dagger.Lazy
import io.reactivex.Single

class AlbumNetworkDataSource(val networkService: Lazy<NetworkService>) : AlbumDataSource {
    override fun getAlbums(userId: Long): Single<List<Album>> = networkService.get().getUserAlbum(userId)
}
