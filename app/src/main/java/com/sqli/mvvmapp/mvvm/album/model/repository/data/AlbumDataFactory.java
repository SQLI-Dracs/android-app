package com.sqli.mvvmapp.mvvm.album.model.repository.data;

import com.sqli.mvvmapp.common.cache.DBAdapter;
import com.sqli.mvvmapp.common.network.NetworkService;
import com.sqli.mvvmapp.mvvm.album.model.repository.datasource.AlbumDBDataSource;
import com.sqli.mvvmapp.mvvm.album.model.repository.datasource.AlbumNetworkDataSource;

import javax.inject.Inject;

import dagger.Lazy;

public class AlbumDataFactory {


    private final Lazy<NetworkService> networkService;
    private final Lazy<DBAdapter> dbAdapter;

    public @Inject
    AlbumDataFactory(Lazy<NetworkService> networkService, Lazy<DBAdapter> dbAdapter) {
        this.networkService = networkService;
        this.dbAdapter = dbAdapter;
    }

    public AlbumNetworkDataSource createNetworkDataSource() {
        return new AlbumNetworkDataSource(networkService);
    }

    public AlbumDBDataSource createDBDataSource() {
        return new AlbumDBDataSource(dbAdapter);
    }


}
