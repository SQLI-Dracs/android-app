package com.sqli.mvvmapp.utils;

import android.support.annotation.NonNull;

import com.sqli.mvvmapp.mvvm.album.model.entity.Album;
import com.sqli.mvvmapp.mvvm.comments.model.entity.Comment;
import com.sqli.mvvmapp.mvvm.comments.model.entity.PostAndComments;
import com.sqli.mvvmapp.mvvm.photo.model.entity.Photo;
import com.sqli.mvvmapp.mvvm.post.model.entity.Post;
import com.sqli.mvvmapp.mvvm.todo.model.entity.Todo;
import com.sqli.mvvmapp.mvvm.user.model.entity.Company;
import com.sqli.mvvmapp.mvvm.user.model.entity.User;

import java.util.ArrayList;
import java.util.List;

public class FakeDataProvider {

    private static FakeDataProvider instance;

    public static FakeDataProvider getInstance() {
        if (instance == null) {
            instance = new FakeDataProvider();
        }

        return instance;
    }


    private static final String LOREM_IPSUM = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.";

    public List<User> getTestUsers(int numUserObjects) {

        List<User> retUserList = new ArrayList<>();

        User currentUser;
        for (int i = 0; i < numUserObjects; i++) {
            currentUser = new User(i, "name" + i, "username" + i, "email" + i + "@email.com", "+" + i,
                    "user" + i + ".com", new Company("company" + i, "phrase" + i, "bs" + i));

            retUserList.add(currentUser);
        }


        return retUserList;
    }

    public List<Post> getTestUserPosts(long userId, int numPostObjects) {

        List<Post> retPostList = new ArrayList<>();

        Post currentPost;

        for (int i = 0; i < numPostObjects; i++) {
            currentPost = getPost(userId, i);

            retPostList.add(currentPost);
        }

        return retPostList;
    }

    @NonNull
    private Post getPost(long userId, int i) {
        Post currentPost;
        currentPost = new Post(userId, i, "Post" + userId, userId + ":" + i + "-" + LOREM_IPSUM);
        return currentPost;
    }

    public List<Album> getTestUserAlbums(long userId, int numAlbumObjects) {

        List<Album> retAlbumList = new ArrayList<>();

        Album currentAlbum;

        for (int i = 0; i < numAlbumObjects; i++) {
            currentAlbum = new Album(userId, i, userId + ":" + i);

            retAlbumList.add(currentAlbum);
        }

        return retAlbumList;
    }

    /**
     * @param userId
     * @param numAlbumObjects
     * @return
     */
    public List<Todo> getTestTodos(long userId, int numAlbumObjects) {

        List<Todo> retTodoList = new ArrayList<>();

        Todo currentTodo;

        for (int i = 0; i < numAlbumObjects; i++) {
            currentTodo = new Todo(userId, i, userId + ":" + i, i % 2 == 0);
            retTodoList.add(currentTodo);
        }

        return retTodoList;
    }

    public List<Photo> getTestPhotos(long albumId, int numPhotoObjects) {

        List<Photo> retPhotoList = new ArrayList<>();

        Photo currentPhoto;

        for (int i = 0; i < numPhotoObjects; i++) {
            currentPhoto = new Photo(albumId, i, "title " + albumId + ":" + i, albumId + ":" + i, albumId + ":" + i);
            retPhotoList.add(currentPhoto);
        }

        return retPhotoList;
    }

    public Photo getTestPhoto(long id, String title) {
        return new Photo(id, id, title, "", "");
    }

    public List<Comment> getTestComment(long postId, int numCommentObjects) {

        List<Comment> retCommentList = new ArrayList<>();

        Comment retCommentItem;

        for (int i = 0; i < numCommentObjects; i++) {
            retCommentItem = new Comment(i, postId, "name " + i, "email" + i + "@com.com", LOREM_IPSUM);
            retCommentList.add(retCommentItem);
        }

        return retCommentList;
    }

    public PostAndComments getPostAndComments(long idPost, int numComments) {

        Post post = getPost(idPost, (int) idPost);
        List<Comment> commentList = getTestComment(idPost, numComments);

        return new PostAndComments(post, commentList);
    }
}
