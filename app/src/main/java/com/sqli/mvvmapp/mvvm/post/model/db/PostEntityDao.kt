package com.sqli.mvvmapp.mvvm.post.model.db

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import io.reactivex.Single

@Dao
interface PostEntityDao {
    @Query("SELECT * from post where user_id = :userId")
    fun getPosts(userId: Long): Single<List<PostEntity>>

    @Insert
    fun insert(postEntity: PostEntity)

    @Delete
    fun delete(postEntity: PostEntity)
}