package com.sqli.mvvmapp.di.modules

import android.support.v4.app.Fragment
import dagger.Module
import dagger.Provides

@Module
class FragmentModule(private val baseFragment: Fragment) {

    @Provides
    fun provideFragment(): Fragment = this.baseFragment

}