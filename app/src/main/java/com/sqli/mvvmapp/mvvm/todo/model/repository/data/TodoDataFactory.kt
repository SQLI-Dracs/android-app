package com.sqli.mvvmapp.mvvm.todo.model.repository.data

import com.sqli.mvvmapp.common.cache.DBAdapter
import com.sqli.mvvmapp.common.network.NetworkService
import com.sqli.mvvmapp.mvvm.todo.model.repository.datasource.TodoDBDataSource
import com.sqli.mvvmapp.mvvm.todo.model.repository.datasource.TodoNetworkDataSource
import dagger.Lazy
import javax.inject.Inject

class TodoDataFactory
@Inject
constructor(val networkService: Lazy<NetworkService>, val dbAdapter: Lazy<DBAdapter>) {

    fun createNetworkDataSource(): TodoNetworkDataSource = TodoNetworkDataSource(networkService)

    fun createDBDataSource(): TodoDBDataSource = TodoDBDataSource(dbAdapter)

}
