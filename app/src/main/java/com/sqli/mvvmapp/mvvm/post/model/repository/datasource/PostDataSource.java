package com.sqli.mvvmapp.mvvm.post.model.repository.datasource;

import com.sqli.mvvmapp.mvvm.comments.model.db.PostAndCommentsEntity;
import com.sqli.mvvmapp.mvvm.comments.model.entity.Comment;
import com.sqli.mvvmapp.mvvm.comments.model.entity.PostAndComments;
import com.sqli.mvvmapp.mvvm.post.model.entity.Post;

import java.util.List;

import io.reactivex.Maybe;
import io.reactivex.Single;

public interface PostDataSource {

    Single<List<Post>> getPosts(long userId);

    Maybe<PostAndComments> getPostAndComments(long postId);

    Single<List<Comment>> getPostComments(long postId);
}
