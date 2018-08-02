package com.sqli.mvvmapp.mvvm.todo.model.repository.data

import com.sqli.mvvmapp.mvvm.todo.model.db.TodoEntity
import com.sqli.mvvmapp.mvvm.todo.model.entity.Todo
import com.sqli.mvvmapp.mvvm.todo.model.mapper.TodoMapper
import dagger.Lazy
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject

class TodoDataRepository @Inject
constructor(val todoMapper: Lazy<TodoMapper>, val todoDataFactory: Lazy<TodoDataFactory>) : TodoRepository {

    override fun getTodo(userId: Long): Observable<List<Todo>> {

        var dbDataSource = todoDataFactory.get().createDBDataSource()

        var dbSingle: Single<List<Todo>> = dbDataSource.getTodos(userId)
        var nwSingle: Single<List<Todo>> = todoDataFactory.get().createNetworkDataSource().getTodos(userId)
                .flattenAsObservable { todos -> todos }
                .flatMapSingle { todo ->
                    var todoEntity: TodoEntity = todoMapper.get().transform(todo)
                    dbDataSource
                            .delete(todoEntity)
                            .andThen(dbDataSource.insert(todoEntity))
                            .andThen(Single.just(todo))

                }.toList()

        return Observable.mergeDelayError(dbSingle.toObservable(), nwSingle.toObservable())

    }
}
