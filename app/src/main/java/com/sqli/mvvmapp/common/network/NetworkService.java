package com.sqli.mvvmapp.common.network;

import com.sqli.mvvmapp.BuildConfig;
import com.sqli.mvvmapp.mvvm.album.model.entity.Album;
import com.sqli.mvvmapp.mvvm.comments.model.entity.Comment;
import com.sqli.mvvmapp.mvvm.photo.model.entity.Photo;
import com.sqli.mvvmapp.mvvm.post.model.entity.Post;
import com.sqli.mvvmapp.mvvm.todo.model.entity.Todo;
import com.sqli.mvvmapp.mvvm.user.model.entity.User;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Maybe;
import io.reactivex.Single;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkService {

    private RestService restService;

    @Inject
    public NetworkService() {

        OkHttpClient.Builder okHttpBuilder = new OkHttpClient.Builder();

        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            okHttpBuilder.addInterceptor(loggingInterceptor);
        }

        Retrofit retrofit = new Retrofit.Builder().baseUrl(BuildConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpBuilder.build())
                .build();

        restService = retrofit.create(RestService.class);
    }

    public Single<List<User>> getUserList() {
        return restService.getUsers();
    }

    public Single<List<Post>> getUserPost(long userId) {
        return restService.getUserPost(userId);
    }

    public Single<Post> getPost(long postId){
        return restService.getPost(postId);
    }

    public Single<List<Album>> getUserAlbum(long userId) {
        return restService.getUserAlbum(userId);
    }

    public Single<List<Todo>> getUserTodos(long userId) {
        return restService.getUserTodos(userId);
    }

    public Single<List<Photo>> getPhotos(long albumId){
        return restService.getPhotos(albumId);
    }

    public Single<Photo> getPhoto(long photoId){
        return restService.getPhoto(photoId);
    }

    public Single<List<Comment>> getPostComments(long postId) {
        return restService.getPostComments(postId);
    }
}
