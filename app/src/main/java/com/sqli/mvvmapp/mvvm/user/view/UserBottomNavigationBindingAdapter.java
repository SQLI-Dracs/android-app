package com.sqli.mvvmapp.mvvm.user.view;

import android.databinding.BindingAdapter;
import android.support.design.widget.BottomNavigationView;

public class UserBottomNavigationBindingAdapter {
    @BindingAdapter("app:onNavigationItemSelected")
    public static void onNavigationItemSelected(BottomNavigationView bottomNavigationView,
                                                BottomNavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener){
        bottomNavigationView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);
    }

}
