package com.sqli.mvvmapp.di.modules

import android.content.Context
import com.sqli.mvvmapp.MVVMApplication
import com.sqli.mvvmapp.common.utils.ImageUtils
import com.sqli.mvvmapp.mvvm.album.model.repository.data.AlbumDataRepository
import com.sqli.mvvmapp.mvvm.album.model.repository.data.AlbumRepository
import com.sqli.mvvmapp.mvvm.photo.model.repository.data.PhotoDataRepository
import com.sqli.mvvmapp.mvvm.photo.model.repository.data.PhotoRepository
import com.sqli.mvvmapp.mvvm.post.model.repository.data.PostDataRepository
import com.sqli.mvvmapp.mvvm.post.model.repository.data.PostRepository
import com.sqli.mvvmapp.mvvm.todo.model.repository.data.TodoDataRepository
import com.sqli.mvvmapp.mvvm.todo.model.repository.data.TodoRepository
import com.sqli.mvvmapp.mvvm.user.model.repository.data.UserDataRepository
import com.sqli.mvvmapp.mvvm.user.model.repository.data.UserRepository
import dagger.Module
import dagger.Provides

@Module
class ApplicationModule(private val application: MVVMApplication) {

    @Provides
    fun provideApplication(): MVVMApplication = application

    @Provides
    fun provideContext(): Context = application

    @Provides
    fun provideUserRepository(userDataRepository: UserDataRepository): UserRepository = userDataRepository

    @Provides
    fun providePostRepository(postDataRepository: PostDataRepository): PostRepository = postDataRepository

    @Provides
    fun provideAlbumRepository(albumDataRepository: AlbumDataRepository): AlbumRepository = albumDataRepository

    @Provides
    fun provideTodoRepository(todoDataRepository: TodoDataRepository): TodoRepository = todoDataRepository

    @Provides
    fun providePhotoRepository(photoRepository: PhotoDataRepository): PhotoRepository = photoRepository

    @Provides
    fun provideImageUtils() : ImageUtils = ImageUtils()

}