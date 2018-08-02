package com.sqli.mvvmapp.common.cache;

import android.arch.persistence.room.Room;
import android.content.Context;

import com.sqli.mvvmapp.mvvm.album.model.db.AlbumEntity;
import com.sqli.mvvmapp.mvvm.comments.model.db.CommentEntity;
import com.sqli.mvvmapp.mvvm.comments.model.db.PostAndCommentsEntity;
import com.sqli.mvvmapp.mvvm.photo.model.db.PhotoEntity;
import com.sqli.mvvmapp.mvvm.post.model.db.PostEntity;
import com.sqli.mvvmapp.mvvm.todo.model.db.TodoEntity;
import com.sqli.mvvmapp.mvvm.user.model.db.UserEntity;

import java.util.List;

import javax.inject.Inject;

import dagger.Lazy;
import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.Single;

public class DBAdapter {
    private static final String DB_NAME = "MVVMApp";

    private AppDatabase appDatabase;

    @Inject
    DBAdapter(Lazy<Context> context) {
        appDatabase = Room.databaseBuilder(context.get().getApplicationContext(), AppDatabase.class, DB_NAME).build();
    }


    /*
     * Users
     */
    public Single<List<UserEntity>> getUserList() {
        return appDatabase.userEntityDao().getAll();
    }

    public Completable insert(UserEntity user) {
        return Completable.fromAction(() -> appDatabase.userEntityDao().insert(user));
    }

    public Completable delete(UserEntity user) {
        return Completable.fromAction(() -> appDatabase.userEntityDao().deleteUser(user));
    }

    /*
     * Post
     */
    public Single<List<PostEntity>> getPosts(long userId) {
        return appDatabase.postEntityDao().getPosts(userId);
    }

    public Completable insert(PostEntity postEntity) {
        return Completable.fromAction(() -> appDatabase.postEntityDao().insert(postEntity));
    }

    public Completable delete(PostEntity postEntity){
        return Completable.fromAction(() -> appDatabase.postEntityDao().delete(postEntity));
    }

    /*
     * Album
     */
    public Single<List<AlbumEntity>> getAlbums(long userId) {
        return appDatabase.albumEntityDao().getAlbums(userId);
    }

    public Completable insert(AlbumEntity albumEntity) {
        return Completable.fromAction(() -> appDatabase.albumEntityDao().insert(albumEntity));
    }

    public Completable delete(AlbumEntity albumEntity){
        return Completable.fromAction(() -> appDatabase.albumEntityDao().delete(albumEntity));
    }

    /*
     * Todos
     */

    public Single<List<TodoEntity>> getTodos(long userId) {
        return appDatabase.todoEntityDao().getTodos(userId);
    }

    public Completable insert(TodoEntity todoEntity) {
        return Completable.fromAction(() -> appDatabase.todoEntityDao().insert(todoEntity));
    }

    public Completable delete(TodoEntity todoEntity){
        return Completable.fromAction(() -> appDatabase.todoEntityDao().delete(todoEntity));
    }

    /*
     * Photos
     */
    public Single<List<PhotoEntity>> getPhotos(long albumId){
        return appDatabase.photoEntityDao().getPhotos(albumId);
    }
    public Single<PhotoEntity> getPhoto(long photoId){
        return appDatabase.photoEntityDao().getPhoto(photoId);
    }
    public Completable insert(PhotoEntity photoEntity) {
        return Completable.fromAction(() -> appDatabase.photoEntityDao().insert(photoEntity));
    }

    public Completable delete(PhotoEntity photoEntity){
        return Completable.fromAction(() -> appDatabase.photoEntityDao().delete(photoEntity));
    }

    public Completable insert(CommentEntity commentEntity) {
       return Completable.fromAction(() -> appDatabase.commentEntityDao().insert(commentEntity));
    }

    public Completable delete(CommentEntity commentEntity) {
       return Completable.fromAction(() -> appDatabase.commentEntityDao().delete(commentEntity));
    }

    public Single<List<CommentEntity>> getPostComments(long postId) {
        return appDatabase.commentEntityDao().getComments(postId);
    }

    public Maybe<PostAndCommentsEntity> getPostAndComments(long postId){
        return appDatabase.postAndCommentsDao().getPostAndComment(postId);
    }
}
