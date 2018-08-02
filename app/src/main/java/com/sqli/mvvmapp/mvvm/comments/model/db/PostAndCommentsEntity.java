package com.sqli.mvvmapp.mvvm.comments.model.db;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Relation;

import com.sqli.mvvmapp.mvvm.post.model.db.PostEntity;

import java.util.List;


public class PostAndCommentsEntity {

    @Embedded
    private PostEntity post;

    @Relation(parentColumn = "id", entityColumn = "post_id", entity = CommentEntity.class)
    private List<CommentEntity> comments;

    public PostEntity getPost() {
        return post;
    }

    public List<CommentEntity> getComments() {
        return comments;
    }

    public void setPost(PostEntity post) {
        this.post = post;
    }

    public void setComments(List<CommentEntity> comments) {
        this.comments = comments;
    }
}
