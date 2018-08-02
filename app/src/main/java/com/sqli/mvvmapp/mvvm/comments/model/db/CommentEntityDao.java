package com.sqli.mvvmapp.mvvm.comments.model.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import io.reactivex.Single;

@Dao
public interface CommentEntityDao {

    @Query("SELECT * FROM comment WHERE post_id = :postId")
    Single<List<CommentEntity>> getComments(Long postId);

    @Insert
    void insert(CommentEntity commentEntity);

    @Delete
    void delete(CommentEntity commentEntity);

}
