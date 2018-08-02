package com.sqli.mvvmapp.mvvm.user.model.repository.datasource;

import com.sqli.mvvmapp.mvvm.user.model.entity.User;

import java.util.List;

import io.reactivex.Single;

public interface UserDataSource {

    Single<List<User>> userList();

}
