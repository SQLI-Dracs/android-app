package com.sqli.mvvmapp.mvvm.post.model.repository.data;

import com.sqli.mvvmapp.mvvm.comments.model.db.PostAndCommentsEntity;
import com.sqli.mvvmapp.mvvm.comments.model.entity.PostAndComments;
import com.sqli.mvvmapp.mvvm.post.model.entity.Post;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;

public interface PostRepository {

    Observable<List<Post>> getPosts(long userId);

    Observable<PostAndComments> getPostAndComments(long postId);

}
