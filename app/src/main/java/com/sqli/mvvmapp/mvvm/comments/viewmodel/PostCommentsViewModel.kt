package com.sqli.mvvmapp.mvvm.comments.viewmodel

import android.databinding.ObservableField
import com.sqli.mvvmapp.base.BaseListViewModel
import com.sqli.mvvmapp.common.Navigator
import com.sqli.mvvmapp.mvvm.comments.model.entity.Comment
import com.sqli.mvvmapp.mvvm.post.model.entity.Post
import com.sqli.mvvmapp.mvvm.post.model.repository.data.PostRepository
import dagger.Lazy
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class PostCommentsViewModel @Inject constructor(val repository: Lazy<PostRepository>,
                                                val navigator: Lazy<Navigator>) : BaseListViewModel<Comment>() {

    var isLoading: ObservableField<Boolean> = ObservableField(false)
    var post: ObservableField<Post> = ObservableField()

    var postId: Long = -1

    fun start() {
        getPostAndComments()
    }

    fun onRefresh() {
        getPostAndComments()
    }

    fun getPostAndComments() {
        repository.get().getPostAndComments(postId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread(), true)
                .doOnSubscribe { isLoading.set(true) }
                .doAfterTerminate { isLoading.set(false) }
                .subscribeBy (onNext = {
                    items.set(it.comments)
                    post.set(it.post)
                }, onError = {
                    navigator.get().showError(it)
                })
    }

}