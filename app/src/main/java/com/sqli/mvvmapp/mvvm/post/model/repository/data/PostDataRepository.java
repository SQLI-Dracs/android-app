package com.sqli.mvvmapp.mvvm.post.model.repository.data;

import com.sqli.mvvmapp.mvvm.comments.model.db.CommentEntity;
import com.sqli.mvvmapp.mvvm.comments.model.entity.Comment;
import com.sqli.mvvmapp.mvvm.comments.model.entity.PostAndComments;
import com.sqli.mvvmapp.mvvm.comments.model.mapper.CommentMapper;
import com.sqli.mvvmapp.mvvm.post.model.db.PostEntity;
import com.sqli.mvvmapp.mvvm.post.model.entity.Post;
import com.sqli.mvvmapp.mvvm.post.model.mapper.PostMapper;
import com.sqli.mvvmapp.mvvm.post.model.repository.datasource.PostDBDataSource;
import com.sqli.mvvmapp.mvvm.post.model.repository.datasource.PostNetworkDataSource;

import java.util.List;

import javax.inject.Inject;

import dagger.Lazy;
import io.reactivex.Maybe;
import io.reactivex.MaybeSource;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.functions.Function;

public class PostDataRepository implements PostRepository {

    private Lazy<PostMapper> postMapper;
    private Lazy<PostDataFactory> postDataFactory;
    private Lazy<CommentMapper> commentMapper;


    @Inject
    public PostDataRepository(Lazy<PostMapper> postMapper, Lazy<PostDataFactory> postDataFactory,
                              Lazy<CommentMapper> commentMapper) {
        this.postMapper = postMapper;
        this.postDataFactory = postDataFactory;
        this.commentMapper = commentMapper;
    }

    @Override
    public Observable<List<Post>> getPosts(long userId) {

        PostDBDataSource postDBDataSource = postDataFactory.get().createDBDataSource();

        Single<List<Post>> dbSingle = postDBDataSource.getPosts(userId);
        Single<List<Post>> nwSingle = postDataFactory.get().createNetworkDataSource().getPosts(userId)
                .flattenAsObservable(posts -> posts)
                .flatMapSingle((Function<Post, SingleSource<Post>>) post -> {
                    PostEntity postEntity = postMapper.get().transform(post);
                    return postDBDataSource.delete(postEntity)
                            .andThen(postDBDataSource.insert(postEntity))
                            .andThen(Single.just(post));
                })
                .toList();


        return Observable.merge(dbSingle.toObservable(), nwSingle.toObservable());
    }

    @Override
    public Observable<PostAndComments> getPostAndComments(long postId) {

        PostDBDataSource postDBDataSource = postDataFactory.get().createDBDataSource();
        PostNetworkDataSource postNetworkDataSource = postDataFactory.get().createNetworkDataSource();

        Maybe<PostAndComments> dbSingle =  postDBDataSource.getPostAndComments(postId);
        Maybe<PostAndComments> nwSingle = postNetworkDataSource.getPostAndComments(postId)
                .flatMapSingle((Function<PostAndComments, SingleSource<PostAndComments>>) postAndComments -> Observable.fromIterable(postAndComments.getComments())
                        .flatMapMaybe((Function<Comment, MaybeSource<Comment>>) comment -> {

                            CommentEntity commentEntity = commentMapper.get().transform(comment);

                            return postDBDataSource
                                    .delete(commentEntity)
                                    .andThen(postDBDataSource.insert(commentEntity)).andThen(Maybe.just(comment));
                        }).toList().flatMap(comments -> Single.just(postAndComments))).toMaybe();

        return Observable.merge(dbSingle.toObservable(), nwSingle.toObservable());
    }
}
