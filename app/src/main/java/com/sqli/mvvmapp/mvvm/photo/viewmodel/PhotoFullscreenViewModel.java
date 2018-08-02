package com.sqli.mvvmapp.mvvm.photo.viewmodel;

import android.databinding.ObservableField;

import com.sqli.mvvmapp.base.BaseViewModel;
import com.sqli.mvvmapp.common.Navigator;
import com.sqli.mvvmapp.mvvm.photo.model.repository.data.PhotoRepository;

import javax.inject.Inject;

import dagger.Lazy;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class PhotoFullscreenViewModel extends BaseViewModel {

    private Lazy<PhotoRepository> repository;
    private Lazy<Navigator> navigator;

    private ObservableField<String> url = new ObservableField<>();
    private ObservableField<String> title = new ObservableField<>();

    @Inject
    public PhotoFullscreenViewModel(Lazy<PhotoRepository> repository, Lazy<Navigator> navigator) {
        this.repository = repository;
        this.navigator = navigator;
    }

    private long photoId = -1;

    public void start() {
        retievePhoto();
    }

    public void retievePhoto() {
        add(repository.get().getPhoto(photoId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread(), true)
                .subscribe(photo -> {
                            this.url.set(photo.getUrl());
                            this.title.set(photo.getTitle());
                        },
                        throwable -> navigator.get().showError(throwable)));
    }

    public long getPhotoId() {
        return photoId;
    }

    public void setPhotoId(long photoId) {
        this.photoId = photoId;
    }

    public ObservableField<String> getTitle() {
        return title;
    }

    public ObservableField<String> getUrl() {
        return url;
    }

}
