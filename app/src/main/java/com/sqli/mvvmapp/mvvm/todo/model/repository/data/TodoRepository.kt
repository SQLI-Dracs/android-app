package com.sqli.mvvmapp.mvvm.todo.model.repository.data

import com.sqli.mvvmapp.mvvm.post.model.entity.Post
import com.sqli.mvvmapp.mvvm.todo.model.entity.Todo

import io.reactivex.Observable

interface TodoRepository {

    fun getTodo(userId: Long): Observable<List<Todo>>

}
