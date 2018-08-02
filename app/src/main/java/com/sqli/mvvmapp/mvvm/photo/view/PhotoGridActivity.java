package com.sqli.mvvmapp.mvvm.photo.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.sqli.mvvmapp.R;
import com.sqli.mvvmapp.common.Navigator;
import com.sqli.mvvmapp.di.components.DaggerNavigatorComponent;
import com.sqli.mvvmapp.di.components.NavigatorComponent;
import com.sqli.mvvmapp.di.modules.NavigatorModule;

import javax.inject.Inject;

import dagger.Lazy;

public class PhotoGridActivity extends AppCompatActivity {
    public static final String ALBUM_ID = "ALBUM_ID";


    private NavigatorComponent component;
    @Inject
    protected Lazy<Navigator> navigator;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_grid);
        component = DaggerNavigatorComponent.builder().navigatorModule(new NavigatorModule(this)).build();
        component.inject(this);
        navigator.get().showPhotoGridFragment(this, R.id.flRootLayout, getIntent().getLongExtra(ALBUM_ID, -1));
    }

    public NavigatorComponent getNavigatorComponent() {
        return component;
    }
}
