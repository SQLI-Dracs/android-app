package com.sqli.mvvmapp.mvvm.comments.model.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Transaction;

import io.reactivex.Maybe;

@Dao
public interface PostAndCommentsDao {

    @Query("SELECT * FROM post WHERE id = :postId")
    @Transaction
    Maybe<PostAndCommentsEntity> getPostAndComment(long postId);
}
