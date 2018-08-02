package com.sqli.mvvmapp.mvvm.post.model.repository.data;

import com.sqli.mvvmapp.common.cache.DBAdapter;
import com.sqli.mvvmapp.common.network.NetworkService;
import com.sqli.mvvmapp.mvvm.post.model.repository.datasource.PostDBDataSource;
import com.sqli.mvvmapp.mvvm.post.model.repository.datasource.PostNetworkDataSource;

import javax.inject.Inject;

import dagger.Lazy;

public class PostDataFactory {

    private Lazy<NetworkService> networkService;
    private Lazy<DBAdapter> dbAdapter;

    @Inject
    public PostDataFactory(Lazy<NetworkService> networkService, Lazy<DBAdapter> dbAdapter) {
        this.networkService = networkService;
        this.dbAdapter = dbAdapter;
    }

    public PostNetworkDataSource createNetworkDataSource() {
        return new PostNetworkDataSource(networkService);
    }

    public PostDBDataSource createDBDataSource() {
        return new PostDBDataSource(dbAdapter);
    }
}
