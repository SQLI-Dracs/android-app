package com.sqli.mvvmapp.mvvm.album.model.db

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import io.reactivex.Single

@Dao
interface AlbumEntityDao {

    @Query("SELECT * from album where user_id = :userId")
    fun getAlbums(userId: Long): Single<List<AlbumEntity>>

    @Insert
    fun insert(albumEntity: AlbumEntity)

    @Delete
    fun delete(albumEntity: AlbumEntity)
}