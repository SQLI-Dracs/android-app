package com.sqli.mvvmapp.mvvm.user.model.repository.datasource;

import com.sqli.mvvmapp.common.network.NetworkService;
import com.sqli.mvvmapp.mvvm.user.model.entity.User;

import java.util.List;

import dagger.Lazy;
import io.reactivex.Single;

public class UserNetworkDataSource implements UserDataSource {

    private Lazy<NetworkService> networkService;

    public UserNetworkDataSource(Lazy<NetworkService> networkService) {
        this.networkService = networkService;
    }

    @Override
    public Single<List<User>> userList() {
        return networkService.get().getUserList();
    }
}
