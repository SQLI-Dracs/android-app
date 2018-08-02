package com.sqli.mvvmapp.common.network

import com.sqli.mvvmapp.mvvm.album.model.entity.Album
import com.sqli.mvvmapp.mvvm.comments.model.entity.Comment
import com.sqli.mvvmapp.mvvm.photo.model.entity.Photo
import com.sqli.mvvmapp.mvvm.post.model.entity.Post
import com.sqli.mvvmapp.mvvm.todo.model.entity.Todo
import com.sqli.mvvmapp.mvvm.user.model.entity.User
import io.reactivex.Maybe
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface RestService {
    @GET("/users")
    fun getUsers(): Single<List<User>>

    @GET("/users/{userId}/posts")
    fun getUserPost(@Path("userId") userId: Long): Single<List<Post>>

    @GET("/users/{userId}/albums")
    fun getUserAlbum(@Path("userId") userId: Long): Single<List<Album>>

    @GET("/users/{userId}/todos")
    fun getUserTodos(@Path("userId") userId: Long): Single<List<Todo>>

    @GET("/albums/{albumId}/photos")
    fun getPhotos(@Path("albumId") albumId: Long): Single<List<Photo>>

    @GET("/posts/{postId}")
    fun getPost(@Path("postId") postId: Long): Single<Post>

    @GET("/posts/{postId}/comments")
    fun getPostComments(@Path("postId") postId: Long): Single<List<Comment>>

    @GET("/photos/{photoId}")
    fun getPhoto(@Path("photoId") photoId: Long): Single<Photo>

}