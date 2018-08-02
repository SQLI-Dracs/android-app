package com.sqli.mvvmapp.mvvm.todo.viewmodel

import android.databinding.ObservableField
import com.sqli.mvvmapp.base.BaseListViewModel
import com.sqli.mvvmapp.common.Navigator
import com.sqli.mvvmapp.mvvm.todo.model.entity.Todo
import com.sqli.mvvmapp.mvvm.todo.model.repository.data.TodoRepository
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


    fun start() {
        retrieveTodos(userId)
    }

    fun retrieveTodos(userId: Long) {

            todoRepository.get().getTodo(userId)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread(), true)
                    .doOnSubscribe { isLoading.set(true) }
                    .doAfterTerminate { isLoading.set(false) }
                    .subscribeBy(onNext = {
                        items.set(it)
                    }, onError = {
                        navigator.get().showError(it)
                    })
                    .addTo(compositeDisposable)

    }

    fun onRefresh() {
        retrieveTodos(userId)
    }

    fun getIsLoading(): ObservableField<Boolean> {
        return isLoading
    }
}