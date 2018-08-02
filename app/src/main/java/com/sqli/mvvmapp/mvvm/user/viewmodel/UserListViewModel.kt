package com.sqli.mvvmapp.mvvm.user.viewmodel

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
@Inject constructor(private val userRepository: Lazy<UserRepository>,
                    private val navigator: Lazy<Navigator>)
    : BaseListViewModel<User>() {

    var isLoading: ObservableField<Boolean> = ObservableField(false)

    fun start() {
        retrieveUsers()
    }

    fun retrieveUsers() {
        AndroidSchedulers.mainThread().start()

        userRepository.get().users
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread(), true)
                .doOnSubscribe {
                    isLoading.set(true)
                }
                .doAfterTerminate {
                    isLoading.set(false)
                }
                .subscribeBy(onError = {
                    navigator.get().showError(it)
                }, onNext = {
                    items.set(it)
                }).addTo(compositeDisposable)
    }

    fun onRefresh() {
        retrieveUsers()
    }
}