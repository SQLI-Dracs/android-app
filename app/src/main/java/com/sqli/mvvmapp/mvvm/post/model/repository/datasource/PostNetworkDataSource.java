package com.sqli.mvvmapp.mvvm.post.model.repository.datasource;

import com.sqli.mvvmapp.common.network.NetworkService;
import com.sqli.mvvmapp.mvvm.comments.model.entity.Comment;
import com.sqli.mvvmapp.mvvm.comments.model.entity.PostAndComments;
import com.sqli.mvvmapp.mvvm.post.model.entity.Post;

import java.util.List;

import dagger.Lazy;
import io.reactivex.Maybe;
import io.reactivex.MaybeSource;
import io.reactivex.ObservableSource;
import io.reactivex.Single;
import io.reactivex.functions.Function;

public class PostNetworkDataSource implements PostDataSource {

    private Lazy<NetworkService> networkService;

    public PostNetworkDataSource(Lazy<NetworkService> networkService) {
        this.networkService = networkService;
    }

    @Override
    public Single<List<Post>> getPosts(long userId) {
        return networkService.get().getUserPost(userId);
    }

    @Override
    public Maybe<PostAndComments> getPostAndComments(long postId) {
        return networkService.get().getPost(postId).flatMapMaybe(post -> networkService.get().getPostComments(postId)
                .flatMapMaybe(comments -> Maybe.just(new PostAndComments(post, comments))));
    }

    @Override
    public Single<List<Comment>> getPostComments(long postId) {
        return networkService.get().getPostComments(postId);
    }
}
