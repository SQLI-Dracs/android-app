package com.sqli.mvvmapp

import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import android.util.Log
import com.sqli.mvvmapp.common.network.NetworkService
import com.sqli.mvvmapp.mvvm.user.model.entity.User
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import io.reactivex.subscribers.TestSubscriber
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class UserListActivityTest {

    @Test
    fun rxtest() {
        var networkService: NetworkService = NetworkService()

        val networkSingle: Single<List<User>> = networkService.userList
        val defaultSingle: Single<List<User>> = Single.defer {
            Single.just<List<User>>(listOf())
        }

        Observable.mergeDelayError(defaultSingle.toObservable(), networkSingle.toObservable())
                .subscribeOn(Schedulers.io())
                .subscribeBy(onError = {
                    System.out.println("TEST error")
                }, onNext = {
                    System.out.println("TEST items:"+ it.size )
                }).addTo(CompositeDisposable())
    }
}
