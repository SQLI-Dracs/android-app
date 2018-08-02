package com.sqli.mvvmapp;

import android.app.Application;

import com.sqli.mvvmapp.di.components.ApplicationComponent;
import com.sqli.mvvmapp.di.components.DaggerApplicationComponent;
import com.sqli.mvvmapp.di.modules.ApplicationModule;

import io.reactivex.android.schedulers.AndroidSchedulers;

public class MVVMApplication extends Application {

    ApplicationComponent component;

    @Override
    public void onCreate() {
        super.onCreate();

        component = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();

        component.inject(this);


    }

    public ApplicationComponent getComponent() {
        return component;
    }
}
