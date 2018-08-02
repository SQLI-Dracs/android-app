package com.sqli.mvvmapp.mvvm.photo.model.db;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

import com.sqli.mvvmapp.mvvm.album.model.db.AlbumEntity;

@Entity(tableName = "photo",
        foreignKeys = @ForeignKey(entity = AlbumEntity.class,
                parentColumns = "id",
                childColumns = "album_id",
                onDelete = ForeignKey.CASCADE),
        indices = {@Index(value = {"album_id"}, unique = false)})
public class PhotoEntity {
    @PrimaryKey
    private Long id;

    @ColumnInfo(name = "album_id")
    private Long albumId;

    @ColumnInfo(name = "title")
    private String title;

    @ColumnInfo(name = "url")
    private String url;

    @ColumnInfo(name = "thumbnail_url")
    private String thumbnailUrl;

    public PhotoEntity(Long id, Long albumId, String title, String url, String thumbnailUrl) {
        this.id = id;
        this.albumId = albumId;
        this.title = title;
        this.url = url;
        this.thumbnailUrl = thumbnailUrl;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAlbumId() {
        return albumId;
    }

    public void setAlbumId(Long columnId) {
        this.albumId = columnId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }
}
