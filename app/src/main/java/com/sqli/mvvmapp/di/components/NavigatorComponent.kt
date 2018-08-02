package com.sqli.mvvmapp.di.components

import android.app.Activity
import com.sqli.mvvmapp.di.modules.NavigatorModule
import com.sqli.mvvmapp.mvvm.comments.view.PostCommentsActivity
import com.sqli.mvvmapp.mvvm.photo.view.PhotoFullscreenActivity
import com.sqli.mvvmapp.mvvm.photo.view.PhotoGridActivity
import com.sqli.mvvmapp.mvvm.user.view.UserDetailActivity
import com.sqli.mvvmapp.mvvm.user.view.UserListActivity
import dagger.Component

@Component(modules = arrayOf(NavigatorModule::class))
interface NavigatorComponent {

    fun inject(activity: UserListActivity)
    fun inject(activity: UserDetailActivity)
    fun inject(activity: PhotoGridActivity)
    fun inject(activity: PhotoFullscreenActivity)
    fun inject(activity: PostCommentsActivity)

    val activity: Activity

}