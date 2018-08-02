package com.sqli.mvvmapp.mvvm.user.model.repository.datasource;

import com.sqli.mvvmapp.common.cache.DBAdapter;
import com.sqli.mvvmapp.mvvm.user.model.db.UserEntity;
import com.sqli.mvvmapp.mvvm.user.model.entity.Company;
import com.sqli.mvvmapp.mvvm.user.model.entity.User;

import java.util.List;

import dagger.Lazy;
import io.reactivex.Completable;
import io.reactivex.Single;

public class UserDBDataSource implements UserDataSource {

    private Lazy<DBAdapter> dbAdapter;

    public UserDBDataSource(Lazy<DBAdapter> dbAdapter) {
        this.dbAdapter = dbAdapter;
    }

    @Override
    public Single<List<User>> userList() {

        return dbAdapter.get().getUserList()
                .flattenAsObservable(users -> users)
                .map(userEntity -> {
                    Company company = new Company(userEntity.getCompany().getName(),
                            userEntity.getCompany().getCatchPhrase(),
                            userEntity.getCompany().getBs());

                    User user = new User(userEntity.getId(),
                            userEntity.getName(),
                            userEntity.getUsername(),
                            userEntity.getEmail(),
                            userEntity.getPhone(),
                            userEntity.getWebsite(),
                            company);
                    return user;
                }).toList();

    }


    public Completable insert(UserEntity user) {
        return dbAdapter.get().insert(user);
    }

    public Completable delete(UserEntity user) {
        return dbAdapter.get().delete(user);
    }
}
