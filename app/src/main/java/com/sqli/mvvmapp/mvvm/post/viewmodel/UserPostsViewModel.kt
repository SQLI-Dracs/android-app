package com.sqli.mvvmapp.mvvm.post.viewmodel

import android.databinding.ObservableField
import com.sqli.mvvmapp.base.BaseListViewModel
import com.sqli.mvvmapp.common.Navigator
import com.sqli.mvvmapp.mvvm.post.model.entity.Post
import com.sqli.mvvmapp.mvvm.post.model.repository.data.PostRepository
import dagger.Lazy
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


class UserPostsViewModel
@Inject constructor(private val postRepository: Lazy<PostRepository>,
                    private val navigator: Lazy<Navigator>)
    : BaseListViewModel<Post>() {
    var isLoading: ObservableField<Boolean> = ObservableField(false)
    var userId: Long = -1

    fun start() {
        retrievePosts()
    }

    fun retrievePosts() {
        postRepository.get().getPosts(userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread(), true)
                .doOnSubscribe {
                    isLoading.set(true)
                }
                .doAfterTerminate {
                    isLoading.set(false)
                }
                .subscribeBy(onNext = {
                    items.set(it)
                }, onError = {
                    navigator.get().showError(it)
                }).addTo(compositeDisposable)
    }

    fun onRefresh() {
        retrievePosts()
    }
}