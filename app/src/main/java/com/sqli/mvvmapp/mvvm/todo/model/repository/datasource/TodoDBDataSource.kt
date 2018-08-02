package com.sqli.mvvmapp.mvvm.todo.model.repository.datasource

import com.sqli.mvvmapp.common.cache.DBAdapter
import com.sqli.mvvmapp.mvvm.todo.model.db.TodoEntity
import com.sqli.mvvmapp.mvvm.todo.model.entity.Todo
import dagger.Lazy
import io.reactivex.Completable
import io.reactivex.Single

class TodoDBDataSource(val dbAdapter: Lazy<DBAdapter>) : TodoDataSource {
    override fun getTodos(userId: Long): Single<List<Todo>> = dbAdapter.get().getTodos(userId)
            .flattenAsObservable { todos -> todos }
            .map { Todo(it.userId, it.id, it.title, it.isCompleted) }
            .toList()

    fun insert(todoEntity: TodoEntity): Completable = dbAdapter.get().insert(todoEntity)

    fun delete(todoEntity: TodoEntity): Completable = dbAdapter.get().delete(todoEntity)
}