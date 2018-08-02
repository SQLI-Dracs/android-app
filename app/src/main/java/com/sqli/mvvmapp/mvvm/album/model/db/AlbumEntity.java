package com.sqli.mvvmapp.mvvm.album.model.db;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

import com.sqli.mvvmapp.mvvm.user.model.db.UserEntity;

@Entity(tableName = "album",
        foreignKeys = @ForeignKey(entity = UserEntity.class,
        parentColumns = "id",
        childColumns = "user_id",
        onDelete = ForeignKey.CASCADE),
        indices = {@Index(value = {"user_id"}, unique = false)})
public class AlbumEntity {

    @PrimaryKey
    private Long id;

    @ColumnInfo(name = "user_id")
    private Long userId;

    @ColumnInfo(name = "title")
    private String title;

    public AlbumEntity(Long userId, Long id, String title) {
        this.userId = userId;
        this.id = id;
        this.title = title;
    }

    public Long getUserId() {
        return userId;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
