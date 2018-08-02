package com.sqli.mvvmapp.common;

import android.accounts.NetworkErrorException;
import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.widget.Toast;

import com.sqli.mvvmapp.R;
import com.sqli.mvvmapp.mvvm.album.model.entity.Album;
import com.sqli.mvvmapp.mvvm.comments.view.PostCommentsActivity;
import com.sqli.mvvmapp.mvvm.comments.view.PostCommentsFragment;
import com.sqli.mvvmapp.mvvm.photo.model.entity.Photo;
import com.sqli.mvvmapp.mvvm.photo.view.PhotoFullscreenActivity;
import com.sqli.mvvmapp.mvvm.photo.view.PhotoFullscreenFragment;
import com.sqli.mvvmapp.mvvm.photo.view.PhotoGridActivity;
import com.sqli.mvvmapp.mvvm.photo.view.PhotoGridFragment;
import com.sqli.mvvmapp.mvvm.post.model.entity.Post;
import com.sqli.mvvmapp.mvvm.post.view.UserPostsFragment;
import com.sqli.mvvmapp.mvvm.todo.view.UserTodosFragment;
import com.sqli.mvvmapp.mvvm.user.model.entity.User;
import com.sqli.mvvmapp.mvvm.user.view.UserDetailActivity;
import com.sqli.mvvmapp.mvvm.user.view.UserListFragment;
import com.sqli.mvvmapp.mvvm.view.fragment.UserAlbumsFragment;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.inject.Inject;

import dagger.Lazy;

public class Navigator {

    private static final String USER_LIST = "USER_LIST";
    private static final String USER_POSTS = "USER_POSTS";
    private static final String USER_ALBUMS = "USER_ALBUMS";
    private static final String USER_TODOS = "USER_TODOS";
    private static final String PHOTO_GRID = "PHOTO_GRID";
    private static final String POST_COMMENTS = "POST_COMMENTS";

    private Lazy<Activity> activity;

    @Inject
    public Navigator(Lazy<Activity> activity) {
        this.activity = activity;
    }

    /**
     * User List
     *
     * @param activity
     * @param container
     */
    public void showUserListFragment(FragmentActivity activity, int container) {

        UserListFragment userListFragment = UserListFragment.newInstance();

        FragmentTransaction fragmentTransaction = activity.getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(container, userListFragment, USER_LIST);
        fragmentTransaction.commit();

    }

    /**
     * User Detail Activity
     *
     * @param user
     */
    public void showUserDetailActivity(User user) {

        Intent userDetailIntent = new Intent(activity.get(), UserDetailActivity.class);

        userDetailIntent.putExtra(UserDetailActivity.USER_ID, user.getId());
        userDetailIntent.putExtra(UserDetailActivity.USER_NAME, user.getName());
        activity.get().startActivity(userDetailIntent);

    }

    /**
     * Posts
     *
     * @param activity
     * @param container
     * @param userId
     */
    public void showUserPostsFragment(FragmentActivity activity, int container, long userId) {

        UserPostsFragment userPostsFragment = UserPostsFragment.newInstance(userId);

        FragmentTransaction fragmentTransaction = activity.getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(container, userPostsFragment, USER_POSTS);
        fragmentTransaction.commit();

    }

    /**
     * Albums
     *
     * @param activity
     * @param container
     * @param userId
     */
    public void showUserAlbumsFragment(FragmentActivity activity, int container, long userId) {

        UserAlbumsFragment userAlbumsFragment = UserAlbumsFragment.Companion.newInstance(userId);

        FragmentTransaction fragmentTransaction = activity.getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(container, userAlbumsFragment, USER_ALBUMS);
        fragmentTransaction.commit();

    }

    /**
     * Todos
     *
     * @param activity
     * @param container
     * @param userId
     */
    public void showUserTodosFragment(FragmentActivity activity, int container, long userId) {
        UserTodosFragment userTodosFragment = UserTodosFragment.Companion.newInstance(userId);

        FragmentTransaction fragmentTransaction = activity.getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(container, userTodosFragment, USER_TODOS);
        fragmentTransaction.commit();
    }

    /**
     * Gallery activity
     *
     * @param album
     */
    public void showPhotoGridActivity(Album album) {
        Intent photoGridIntent = new Intent(activity.get(), PhotoGridActivity.class);

        photoGridIntent.putExtra(PhotoGridActivity.ALBUM_ID, album.getId());
        activity.get().startActivity(photoGridIntent);

    }

    /**
     * Gallery fragment
     * @param activity
     * @param container
     * @param albumId
     */
    public void showPhotoGridFragment(FragmentActivity activity, int container, long albumId) {
        PhotoGridFragment photoGridFragment = PhotoGridFragment.Companion.newInstance(albumId);

        FragmentTransaction fragmentTransaction = activity.getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(container, photoGridFragment, PHOTO_GRID);
        fragmentTransaction.commit();
    }

    /**
     * It opens fullscreen image activity
     * @param photo
     */
    public void showPhotoFullscreenActivity(Photo photo) {
        Intent photoFullscreenIntent = new Intent(activity.get(), PhotoFullscreenActivity.class);

        photoFullscreenIntent.putExtra(PhotoFullscreenActivity.PHOTO_ID, photo.getId());
        activity.get().startActivity(photoFullscreenIntent);
    }


    /**
     * Fullscreen image fragment
     * @param fragmentActivity
     * @param flRootLayout
     * @param photoId
     */
    public void showPhotoFullscreenFragment(FragmentActivity fragmentActivity, int flRootLayout, Long photoId) {
        PhotoFullscreenFragment photoFullScreenFragment = PhotoFullscreenFragment.newInstance(photoId);

        FragmentTransaction fragmentTransaction = fragmentActivity.getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(flRootLayout, photoFullScreenFragment, PHOTO_GRID);
        fragmentTransaction.commit();
    }

    /**
     * It shows the Post detail (comment list) activity
     * @param post
     */
    public void showCommentsActivity(Post post){
        Intent commentsActivityIntent = new Intent(activity.get(), PostCommentsActivity.class);

        commentsActivityIntent.putExtra(PostCommentsActivity.POST_ID, post.getId());

        activity.get().startActivity(commentsActivityIntent);
    }

    /**
     * It shows the Post detail (comment list) fragment
     * @param fragmentActivity
     * @param flRootLayout
     * @param postId
     */
    public void showCommentsFragment(FragmentActivity fragmentActivity, int flRootLayout, Long postId){
        PostCommentsFragment postCommentsFragment = PostCommentsFragment.Companion.newInstance(postId);

        FragmentTransaction fragmentTransaction = fragmentActivity.getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(flRootLayout, postCommentsFragment, PHOTO_GRID);
        fragmentTransaction.commit();

    }

    public void showError(Throwable throwable) {

        if (throwable instanceof NetworkErrorException) {
            Toast.makeText(activity.get(), activity.get().getString(R.string.exception_internet), Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(activity.get(), throwable.getMessage(), Toast.LENGTH_LONG).show();
        }
    }



}
