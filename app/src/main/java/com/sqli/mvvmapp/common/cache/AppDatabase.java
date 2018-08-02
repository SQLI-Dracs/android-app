package com.sqli.mvvmapp.common.cache;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.sqli.mvvmapp.mvvm.album.model.db.AlbumEntity;
import com.sqli.mvvmapp.mvvm.album.model.db.AlbumEntityDao;
import com.sqli.mvvmapp.mvvm.comments.model.db.CommentEntity;
import com.sqli.mvvmapp.mvvm.comments.model.db.CommentEntityDao;
import com.sqli.mvvmapp.mvvm.comments.model.db.PostAndCommentsDao;
import com.sqli.mvvmapp.mvvm.photo.model.db.PhotoEntity;
import com.sqli.mvvmapp.mvvm.photo.model.db.PhotoEntityDao;
import com.sqli.mvvmapp.mvvm.post.model.db.PostEntity;
import com.sqli.mvvmapp.mvvm.post.model.db.PostEntityDao;
import com.sqli.mvvmapp.mvvm.todo.model.db.TodoEntity;
import com.sqli.mvvmapp.mvvm.todo.model.db.TodoEntityDao;
import com.sqli.mvvmapp.mvvm.user.model.db.UserEntity;
import com.sqli.mvvmapp.mvvm.user.model.db.UserEntityDao;

@Database(version = 1, entities = {UserEntity.class, PostEntity.class, AlbumEntity.class,
        TodoEntity.class, PhotoEntity.class, CommentEntity.class})
public abstract class AppDatabase extends RoomDatabase {

    public abstract UserEntityDao userEntityDao();

    public abstract PostEntityDao postEntityDao();

    public abstract AlbumEntityDao albumEntityDao();

    public abstract TodoEntityDao todoEntityDao();

    public abstract PhotoEntityDao photoEntityDao();

    public abstract CommentEntityDao commentEntityDao();

    public abstract PostAndCommentsDao postAndCommentsDao();
}
