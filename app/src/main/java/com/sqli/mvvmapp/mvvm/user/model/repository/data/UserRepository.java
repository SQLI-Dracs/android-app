package com.sqli.mvvmapp.mvvm.user.model.repository.data;

import com.sqli.mvvmapp.mvvm.user.model.entity.User;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;

public interface UserRepository {

    Observable<List<User>> getUsers();
}
