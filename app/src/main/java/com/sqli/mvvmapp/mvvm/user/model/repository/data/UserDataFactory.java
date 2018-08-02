package com.sqli.mvvmapp.mvvm.user.model.repository.data;

import com.sqli.mvvmapp.common.cache.DBAdapter;
import com.sqli.mvvmapp.common.network.NetworkService;
import com.sqli.mvvmapp.mvvm.user.model.repository.datasource.UserDBDataSource;
import com.sqli.mvvmapp.mvvm.user.model.repository.datasource.UserNetworkDataSource;

import javax.inject.Inject;

import dagger.Lazy;

public class UserDataFactory {

    private Lazy<NetworkService> networkService;
    private Lazy<DBAdapter> dbAdapter;

    @Inject
    public UserDataFactory(Lazy<NetworkService> networkService, Lazy<DBAdapter> dbAdapter) {
        this.networkService = networkService;
        this.dbAdapter = dbAdapter;
    }

    public UserNetworkDataSource createNetworkDataSource() {
        return new UserNetworkDataSource(networkService);
    }

    public UserDBDataSource createDBDataSource() {
        return new UserDBDataSource(dbAdapter);
    }
}
