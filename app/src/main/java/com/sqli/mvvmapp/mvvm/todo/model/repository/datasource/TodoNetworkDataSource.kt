package com.sqli.mvvmapp.mvvm.todo.model.repository.datasource

import com.sqli.mvvmapp.common.network.NetworkService
import com.sqli.mvvmapp.mvvm.todo.model.entity.Todo
import io.reactivex.Single
import dagger.Lazy

class TodoNetworkDataSource(val networkService: Lazy<NetworkService>) : TodoDataSource{
    override fun getTodos(userId: Long): Single<List<Todo>> = networkService.get().getUserTodos(userId)
}