package com.sqli.mvvmapp.di.components

import com.sqli.mvvmapp.di.modules.FragmentModule
import com.sqli.mvvmapp.mvvm.comments.view.PostCommentsFragment
import com.sqli.mvvmapp.mvvm.photo.view.PhotoFullscreenFragment
import com.sqli.mvvmapp.mvvm.photo.view.PhotoGridFragment
import com.sqli.mvvmapp.mvvm.post.view.UserPostsFragment
import com.sqli.mvvmapp.mvvm.todo.view.UserTodosFragment
import com.sqli.mvvmapp.mvvm.user.view.UserListFragment
import com.sqli.mvvmapp.mvvm.view.fragment.UserAlbumsFragment
import dagger.Component

@Component(dependencies = arrayOf(ApplicationComponent::class, NavigatorComponent::class), modules = arrayOf(FragmentModule::class))
interface FragmentComponent {

    fun inject(fragment: UserListFragment)
    fun inject(fragment: UserPostsFragment)
    fun inject(fragment: UserAlbumsFragment)
    fun inject(fragment: UserTodosFragment)
    fun inject(fragment: PhotoGridFragment)
    fun inject(fragment: PhotoFullscreenFragment)
    fun inject(fragment: PostCommentsFragment)

}