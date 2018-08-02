package com.sqli.mvvmapp.mvvm.todo.model.repository.datasource

import com.sqli.mvvmapp.mvvm.todo.model.entity.Todo
import io.reactivex.Single

interface TodoDataSource {
    fun getTodos(userId: Long): Single<List<Todo>>
}