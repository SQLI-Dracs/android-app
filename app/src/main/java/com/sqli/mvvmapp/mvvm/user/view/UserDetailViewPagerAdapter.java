package com.sqli.mvvmapp.mvvm.user.view;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.sqli.mvvmapp.mvvm.post.view.UserPostsFragment;
import com.sqli.mvvmapp.mvvm.todo.view.UserTodosFragment;
import com.sqli.mvvmapp.mvvm.view.fragment.UserAlbumsFragment;

import java.lang.ref.WeakReference;

public class UserDetailViewPagerAdapter extends FragmentStatePagerAdapter {

    public static final int FRAGMENT_POST = 0;
    public static final int FRAGMENT_ALBUM = 1;
    public static final int FRAGMENT_TODO = 2;

    public static final int NUM_FRAGMENTS = 3;

    private WeakReference<FragmentManager> fragmentManager;

    private long userId;

    public UserDetailViewPagerAdapter(FragmentManager fm, long userId) {
        super(fm);
        this.fragmentManager = new WeakReference<>(fm);
        this.userId = userId;
    }

    @Override
    public Fragment getItem(int position) {

        Fragment fragment;


        switch (position) {
            case FRAGMENT_POST:
                fragment = fragmentManager.get().findFragmentByTag(UserPostsFragment.class.getName());
                if (fragment == null) {
                    fragment = UserPostsFragment.newInstance(userId);
                }
                break;

            case FRAGMENT_ALBUM:
                fragment = fragmentManager.get().findFragmentByTag(UserAlbumsFragment.class.getName());
                if (fragment == null) {
                    fragment = UserAlbumsFragment.Companion.newInstance(userId);
                }
                break;

            case FRAGMENT_TODO:
                fragment = fragmentManager.get().findFragmentByTag(UserTodosFragment.class.getName());
                if (fragment == null) {
                    fragment = UserTodosFragment.Companion.newInstance(userId);
                }
                break;
            default:
                fragment = null;
        }

        return fragment;
    }

    @Override
    public int getCount() {
        return NUM_FRAGMENTS;
    }
}
