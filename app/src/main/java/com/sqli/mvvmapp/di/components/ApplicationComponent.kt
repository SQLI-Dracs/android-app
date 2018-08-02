package com.sqli.mvvmapp.di.components

import com.sqli.mvvmapp.MVVMApplication
import com.sqli.mvvmapp.common.utils.ImageUtils
import com.sqli.mvvmapp.di.modules.ApplicationModule
import com.sqli.mvvmapp.mvvm.album.model.repository.data.AlbumRepository
import com.sqli.mvvmapp.mvvm.photo.model.repository.data.PhotoRepository
import com.sqli.mvvmapp.mvvm.post.model.repository.data.PostRepository
import com.sqli.mvvmapp.mvvm.todo.model.repository.data.TodoRepository
import com.sqli.mvvmapp.mvvm.user.model.repository.data.UserRepository
import dagger.Component

@Component(modules = arrayOf(ApplicationModule::class))
interface ApplicationComponent {

    fun inject(application: MVVMApplication)

    val application: MVVMApplication

    fun userRepository(): UserRepository

    fun postRepository(): PostRepository

    fun albumRepository(): AlbumRepository

    fun todoRepository(): TodoRepository

    fun photoRepository(): PhotoRepository

    fun imageUtils(): ImageUtils
}