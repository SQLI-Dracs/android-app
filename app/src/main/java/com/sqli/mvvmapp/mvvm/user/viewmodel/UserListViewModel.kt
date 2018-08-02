package com.sqli.mvvmapp.mvvm.user.viewmodel

import android.app.Activity
import android.databinding.ObservableField
import com.sqli.mvvmapp.base.BaseListViewModel
import com.sqli.mvvmapp.common.Navigator
import com.sqli.mvvmapp.mvvm.user.model.entity.User
import com.sqli.mvvmapp.mvvm.user.model.repository.data.UserRepository
import dagger.Lazy
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


class UserListViewModel
@Inject constructor(private val userRepository: Lazy<UserRepository>)
    : BaseListViewModel<User>() {

    lateinit var activity: Activity
    var isLoading: ObservableField<Boolean> = ObservableField(false)

    fun start() {
        retrieveUsers()
    }

    fun retrieveUsers() {
        AndroidSchedulers.mainThread().start()

        Thread(Runnable {
            items.set(userRepository.get().users)
        }).start()
    }

    fun onRefresh() {
        retrieveUsers()
    }
}