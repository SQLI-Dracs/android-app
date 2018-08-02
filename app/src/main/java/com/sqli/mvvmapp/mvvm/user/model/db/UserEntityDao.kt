package com.sqli.mvvmapp.mvvm.user.model.db

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import io.reactivex.Maybe
import io.reactivex.Single

@Dao
interface UserEntityDao {

    @Query("SELECT * FROM user")
    fun getAll(): Single<List<UserEntity>>

    @Insert
    fun insert(user: UserEntity)

    @Delete
    fun deleteUser(user: UserEntity)

}