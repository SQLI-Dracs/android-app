package com.sqli.mvvmapp.mvvm.photo.viewmodel

import android.databinding.ObservableField
import com.sqli.mvvmapp.base.BaseListViewModel
import com.sqli.mvvmapp.common.Navigator
import com.sqli.mvvmapp.mvvm.photo.model.entity.Photo
import com.sqli.mvvmapp.mvvm.photo.model.repository.data.PhotoRepository
import javax.inject.Inject
import dagger.Lazy
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

class PhotoGridViewModel
@Inject constructor(private val photoRepository: Lazy<PhotoRepository>,
                    private val navigator: Lazy<Navigator>)
    : BaseListViewModel<Photo>(){

    var albumId: Long = -1
    var isLoading: ObservableField<Boolean> = ObservableField(false)


    fun start(){
        retrievePhotos()
    }

    fun retrievePhotos(){
        photoRepository.get().getPhotos(albumId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread(), true)
                .doOnSubscribe { isLoading.set(true) }
                .doAfterTerminate { isLoading.set(false) }
                .subscribeBy(onNext = {
                    items.set(it)
                }, onError = {
                    navigator.get().showError(it)
                }).addTo(compositeDisposable)
    }

     fun onRefresh() {
        retrievePhotos()
    }
}