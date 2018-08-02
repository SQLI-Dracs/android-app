package com.sqli.mvvmapp.mvvm.album.viewmodel;

import android.databinding.ObservableField;

import com.sqli.mvvmapp.base.BaseListViewModel;
import com.sqli.mvvmapp.common.Navigator;
import com.sqli.mvvmapp.mvvm.album.model.entity.Album;
import com.sqli.mvvmapp.mvvm.album.model.repository.data.AlbumRepository;

import javax.inject.Inject;

import dagger.Lazy;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.Nullable;
import io.reactivex.schedulers.Schedulers;

public class UserAlbumsViewModel extends BaseListViewModel<Album> {

    private Lazy<AlbumRepository> albumRepository;
    private Lazy<Navigator> navigator;

    private ObservableField<Boolean> isLoading = new ObservableField<Boolean>(false);
    private Long userId = null;

    public void start() {
        retrieveAlbums(userId);
    }

    @Inject
    public UserAlbumsViewModel(Lazy<AlbumRepository> albumRepository,
                               Lazy<Navigator> navigator) {
        this.albumRepository = albumRepository;
        this.navigator = navigator;
    }

    public void retrieveAlbums(@Nullable Long userId) {
        if (userId != null) {

            add(albumRepository.get().getAlbums(userId)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread(), true)
                    .doOnSubscribe(disposable -> isLoading.set(true))
                    .doAfterTerminate(() -> isLoading.set(false))
                    .subscribe(albums -> getItems().set(albums),
                            throwable -> navigator.get().showError(throwable)));

        }
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void onRefresh(){
        retrieveAlbums(userId);
    }

    public ObservableField<Boolean> getIsLoading() {
        return isLoading;
    }
}
