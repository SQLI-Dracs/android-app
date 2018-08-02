package com.sqli.mvvmapp.mvvm.photo.model.db

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import com.sqli.mvvmapp.mvvm.album.model.db.AlbumEntity
import io.reactivex.Single

@Dao
interface PhotoEntityDao {

    @Query("SELECT * from photo where album_id = :albumId")
    fun getPhotos(albumId: Long): Single<List<PhotoEntity>>

    @Query("SELECT * from photo where id = :photoId")
    fun getPhoto(photoId: Long): Single<PhotoEntity>

    @Insert
    fun insert(photoEntity: PhotoEntity)

    @Delete
    fun delete(photoEntity: PhotoEntity)
}