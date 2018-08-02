package com.sqli.mvvmapp.mvvm.post.view;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.databinding.library.baseAdapters.BR;
import com.jaumard.recyclerviewbinding.BindableRecyclerAdapter;
import com.sqli.mvvmapp.MVVMApplication;
import com.sqli.mvvmapp.R;
import com.sqli.mvvmapp.common.Navigator;
import com.sqli.mvvmapp.databinding.FragmentUserPostsBinding;
import com.sqli.mvvmapp.di.components.DaggerFragmentComponent;
import com.sqli.mvvmapp.di.components.FragmentComponent;
import com.sqli.mvvmapp.di.modules.FragmentModule;
import com.sqli.mvvmapp.mvvm.post.model.entity.Post;
import com.sqli.mvvmapp.mvvm.post.viewmodel.UserPostsViewModel;
import com.sqli.mvvmapp.mvvm.user.view.UserDetailActivity;

import javax.inject.Inject;

import dagger.Lazy;

public class UserPostsFragment extends Fragment
        implements BindableRecyclerAdapter.OnClickListener<Post> {

    public static final String USER_ID = "USER_ID";

    private FragmentUserPostsBinding fragmentUserPostsBinding;

    @Inject
    UserPostsViewModel userPostsViewModel;

    @Inject
    Lazy<Navigator> navigator;

    public static UserPostsFragment newInstance(long userId) {
        UserPostsFragment userPostsFragment = new UserPostsFragment();

        Bundle fragmentArgs = new Bundle();
        fragmentArgs.putLong(USER_ID, userId);

        userPostsFragment.setArguments(fragmentArgs);

        return userPostsFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_user_posts, container, false);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (getActivity() != null && getActivity().getApplication() != null) {
            FragmentComponent fragmentComponent = DaggerFragmentComponent.builder()
                    .fragmentModule(new FragmentModule(this))
                    .navigatorComponent(((UserDetailActivity)getActivity()).getNavigatorComponent())
                    .applicationComponent(((MVVMApplication) getActivity().getApplication()).getComponent())
                    .build();

            fragmentComponent.inject(this);

            if (getView() != null) {
                fragmentUserPostsBinding = DataBindingUtil.bind(getView());
            }

            if (fragmentUserPostsBinding != null) {
                fragmentUserPostsBinding.setClickHandler(this);
                fragmentUserPostsBinding.setViewModel(userPostsViewModel);
            }
            if (getArguments() != null) {
                userPostsViewModel.setUserId(getArguments().getLong(USER_ID));
                userPostsViewModel.start();
            }
        }
    }

    @Override
    public void onClick(Post post) {
        navigator.get().showCommentsActivity(post);
    }

    @Override
    public void onStop() {
        super.onStop();
        userPostsViewModel.clear();
    }
}