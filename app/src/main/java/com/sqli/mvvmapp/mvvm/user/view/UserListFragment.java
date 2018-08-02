package com.sqli.mvvmapp.mvvm.user.view;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.databinding.library.baseAdapters.BR;
import com.jaumard.recyclerviewbinding.BindableRecyclerAdapter;
import com.sqli.mvvmapp.MVVMApplication;
import com.sqli.mvvmapp.R;
import com.sqli.mvvmapp.common.Navigator;
import com.sqli.mvvmapp.databinding.FragmentUserListBinding;
import com.sqli.mvvmapp.di.components.DaggerFragmentComponent;
import com.sqli.mvvmapp.di.components.FragmentComponent;
import com.sqli.mvvmapp.di.modules.FragmentModule;
import com.sqli.mvvmapp.mvvm.user.model.entity.User;
import com.sqli.mvvmapp.mvvm.user.viewmodel.UserListViewModel;

import javax.inject.Inject;

import dagger.Lazy;


public class UserListFragment extends Fragment
        implements BindableRecyclerAdapter.OnClickListener<User> {

    private FragmentUserListBinding fragmentUserListBinding;

    private SwipeRefreshLayout swipeRefreshLayout;

    @Inject
    UserListViewModel userListViewModel;

    Lazy<Navigator> navigator;


    public static UserListFragment newInstance() {
        return new UserListFragment();
    }

    @Nullable
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        navigator = () -> new Navigator(this::getActivity);

        return inflater.inflate(R.layout.fragment_user_list, container, false);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (getActivity() != null && getActivity().getApplication() != null) {

            FragmentComponent fragmentComponent = DaggerFragmentComponent.builder()
                    .fragmentModule(new FragmentModule(this))
                    .navigatorComponent(((UserListActivity)getActivity()).getNavigatorComponent())
                    .applicationComponent(((MVVMApplication) getActivity().getApplication()).getComponent())
                    .build();

            fragmentComponent.inject(this);


            if (getView() != null) {
                swipeRefreshLayout = getView().findViewById(R.id.swrlUserRefresh);
                fragmentUserListBinding = DataBindingUtil.bind(getView());
            }
            if (fragmentUserListBinding != null) {
                fragmentUserListBinding.setClickHandler(this);
                fragmentUserListBinding.setVariable(BR.title, getString(R.string.list_users_title));
                fragmentUserListBinding.setViewModel(userListViewModel);
            }
            userListViewModel.start();
        }
    }

    @Override
    public void onClick(User user) {
        navigator.get().showUserDetailActivity(user);
    }

    @Override
    public void onStop() {
        super.onStop();
        userListViewModel.clear();
    }
}
