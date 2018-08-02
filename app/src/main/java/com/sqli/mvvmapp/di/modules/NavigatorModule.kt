package com.sqli.mvvmapp.di.modules

import android.app.Activity
import dagger.Module
import dagger.Provides

@Module
class NavigatorModule(val activity: Activity) {

    @Provides
    fun provideActivity(): Activity = activity
}