package com.sqli.mvvmapp.mvvm.todo.model.mapper

import com.sqli.mvvmapp.mvvm.todo.model.db.TodoEntity
import com.sqli.mvvmapp.mvvm.todo.model.entity.Todo
import javax.inject.Inject

class TodoMapper @Inject constructor() {
    fun transform(todo: Todo) : TodoEntity {
        val id = todo.id
        val completed = todo.completed
        val title = todo.title
        val userId = todo.userId

        return TodoEntity(userId, id, title, completed)
    }
}