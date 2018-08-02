package com.sqli.mvvmapp.mvvm.todo.viewmodel

import android.databinding.ObservableField
import com.sqli.mvvmapp.base.BaseListViewModel
import com.sqli.mvvmapp.common.Navigator
import com.sqli.mvvmapp.mvvm.todo.model.entity.Todo
import com.sqli.mvvmapp.mvvm.todo.model.repository.data.TodoRepository
import com.sqli.mvvmapp.mvvm.todo.view.UserTodosFragment
import com.sqli.mvvmapp.mvvm.user.view.UserDetailActivity
import dagger.Lazy
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.annotations.Nullable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class UserTodosViewModel @Inject constructor(val todoRepository: Lazy<TodoRepository>, var navigator: Lazy<Navigator>) : BaseListViewModel<Todo>() {

    var userId: Long = -1
    val isLoading = ObservableField(false)

    lateinit var userTodosFragment: UserTodosFragment

    fun start() {
        userTodosFragment.getData()
    }


    fun onRefresh() {
        userTodosFragment.getData()
    }

    fun getIsLoading(): ObservableField<Boolean> {
        return isLoading
    }
}