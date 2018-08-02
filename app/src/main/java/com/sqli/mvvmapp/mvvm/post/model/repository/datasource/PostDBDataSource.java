package com.sqli.mvvmapp.mvvm.post.model.repository.datasource;

import com.sqli.mvvmapp.common.cache.DBAdapter;
import com.sqli.mvvmapp.mvvm.comments.model.db.CommentEntity;
import com.sqli.mvvmapp.mvvm.comments.model.db.PostAndCommentsEntity;
import com.sqli.mvvmapp.mvvm.comments.model.entity.Comment;
import com.sqli.mvvmapp.mvvm.comments.model.entity.PostAndComments;
import com.sqli.mvvmapp.mvvm.post.model.db.PostEntity;
import com.sqli.mvvmapp.mvvm.post.model.entity.Post;

import java.util.List;

import dagger.Lazy;
import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.MaybeSource;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Single;
import io.reactivex.functions.Function;

public class PostDBDataSource implements PostDataSource {

    private Lazy<DBAdapter> dbAdapter;

    public PostDBDataSource(Lazy<DBAdapter> dbAdapter) {
        this.dbAdapter = dbAdapter;
    }

    @Override
    public Single<List<Post>> getPosts(long userId) {
        return dbAdapter.get().getPosts(userId)
                .flattenAsObservable(postEntities -> postEntities)
                .map(postEntity ->
                        new Post(postEntity.getUserId(), postEntity.getId(), postEntity.getTitle(), postEntity.getBody()))
                .toList();
    }

    @Override
    public Maybe<PostAndComments> getPostAndComments(long postId) {

        return dbAdapter.get().getPostAndComments(postId).flatMap((Function<PostAndCommentsEntity, MaybeSource<PostAndComments>>) postAndCommentsEntity -> {

            PostEntity postEntity = postAndCommentsEntity.getPost();
            Post post = new Post(postEntity.getUserId(), postEntity.getId(), postEntity.getTitle(),postEntity.getBody());

            return Observable.fromIterable(postAndCommentsEntity.getComments()).flatMap(commentEntity ->
                    Observable.just(new Comment(commentEntity.getId(), commentEntity.getPostId(), commentEntity.getName(),
                            commentEntity.getEmail(), commentEntity.getBody())))
                    .toList()
                    .flatMapMaybe(comments -> Maybe.just(new PostAndComments(post, comments)));
        });
    }

    public Completable insert(PostEntity postEntity) {
        return dbAdapter.get().insert(postEntity);
    }

    public Completable delete(PostEntity postEntity) {
        return dbAdapter.get().delete(postEntity);
    }

    @Override
    public Single<List<Comment>> getPostComments(long postId) {
        return dbAdapter.get().getPostComments(postId)
                .flattenAsObservable(commentEntities -> commentEntities)
                .map(commentEntity -> new Comment(commentEntity.getId(), commentEntity.getPostId(),
                        commentEntity.getName(), commentEntity.getEmail(), commentEntity.getBody()))
                .toList();
    }

    public Completable insert(CommentEntity commentEntity) {
        return dbAdapter.get().insert(commentEntity);
    }

    public Completable delete(CommentEntity commentEntity) {
        return dbAdapter.get().delete(commentEntity);
    }

}
