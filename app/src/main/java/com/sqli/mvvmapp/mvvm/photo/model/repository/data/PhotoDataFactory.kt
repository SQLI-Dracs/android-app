package com.sqli.mvvmapp.mvvm.photo.model.repository.data

import com.sqli.mvvmapp.common.cache.DBAdapter
import com.sqli.mvvmapp.common.network.NetworkService
import com.sqli.mvvmapp.mvvm.photo.model.repository.datasource.PhotoDBDataSource
import com.sqli.mvvmapp.mvvm.photo.model.repository.datasource.PhotoNetworkDataSource
import dagger.Lazy
import javax.inject.Inject

class PhotoDataFactory @Inject constructor(val networkService: Lazy<NetworkService>, val dbAdapter: Lazy<DBAdapter>){
    fun createNetworkDataSource(): PhotoNetworkDataSource = PhotoNetworkDataSource(networkService)

    fun createDBDataSource(): PhotoDBDataSource = PhotoDBDataSource(dbAdapter)

}