package com.sqli.mvvmapp.mvvm.user.model.repository.data;

import com.sqli.mvvmapp.mvvm.user.model.db.CompanyEntity;
import com.sqli.mvvmapp.mvvm.user.model.db.UserEntity;
import com.sqli.mvvmapp.mvvm.user.model.entity.User;
import com.sqli.mvvmapp.mvvm.user.model.mapper.CompanyMapper;
import com.sqli.mvvmapp.mvvm.user.model.mapper.UserMapper;
import com.sqli.mvvmapp.mvvm.user.model.repository.datasource.UserDBDataSource;

import java.util.List;

import javax.inject.Inject;

import dagger.Lazy;
import io.reactivex.Observable;
import io.reactivex.Single;

public class UserDataRepository implements UserRepository {

    private Lazy<UserMapper> userMapper;
    private Lazy<CompanyMapper> companyMapper;
    private Lazy<UserDataFactory> userDataFactory;

    @Inject
    public UserDataRepository(Lazy<UserMapper> userMapper, Lazy<CompanyMapper> companyMapper, Lazy<UserDataFactory> userDataFactory) {
        this.userMapper = userMapper;
        this.userDataFactory = userDataFactory;
        this.companyMapper = companyMapper;
    }

    @Override
    public List<User> getUsers() {

        Single<List<User>> dbSingle = userDataFactory.get().createDBDataSource().userList();
        Single<List<User>> nwSingle = userDataFactory.get().createNetworkDataSource().userList()
                .flattenAsObservable(users -> users)
                .flatMap(this::saveData).toList();

        return Observable.mergeDelayError(dbSingle.toObservable(), nwSingle.toObservable()).blockingLast();

    }

    private Observable<User> saveData(User user) {
        return Observable.defer(() -> {
            UserEntity currentEntity = userMapper.get().transform(user);
            CompanyEntity companyEntity = companyMapper.get().transform(user.getCompany());
            currentEntity.setCompany(companyEntity);
            UserDBDataSource dbDataSource = userDataFactory.get().createDBDataSource();

            return dbDataSource.delete(currentEntity)
                    .andThen(dbDataSource.insert(currentEntity))
                    .andThen(Observable.just(user));

        });
    }
}
