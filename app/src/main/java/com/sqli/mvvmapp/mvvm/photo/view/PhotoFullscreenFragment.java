package com.sqli.mvvmapp.mvvm.photo.view;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sqli.mvvmapp.base.ToolbarExtensions;
import com.sqli.mvvmapp.databinding.*;

import com.sqli.mvvmapp.MVVMApplication;
import com.sqli.mvvmapp.R;
import com.sqli.mvvmapp.common.utils.ImageUtils;
import com.sqli.mvvmapp.di.components.DaggerFragmentComponent;
import com.sqli.mvvmapp.di.components.FragmentComponent;
import com.sqli.mvvmapp.di.modules.FragmentModule;
import com.sqli.mvvmapp.mvvm.photo.viewmodel.PhotoFullscreenViewModel;

import javax.inject.Inject;

import dagger.Lazy;

public class PhotoFullscreenFragment extends Fragment {

    public static final String PHOTO_ID = "PHOTO_ID";

    public static PhotoFullscreenFragment newInstance(long photoId) {
        PhotoFullscreenFragment photoFullscreenFragment = new PhotoFullscreenFragment();

        Bundle bundle = new Bundle();
        bundle.putLong(PHOTO_ID, photoId);

        photoFullscreenFragment.setArguments(bundle);

        return photoFullscreenFragment;
    }

    @Inject
    protected Lazy<PhotoFullscreenViewModel> photoFullscreenViewModel;

    @Inject
    protected Lazy<ImageUtils> imageUtils;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_photo_fullscreen, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        FragmentComponent fragmentComponent = DaggerFragmentComponent.builder()
                .fragmentModule(new FragmentModule(this))
                .navigatorComponent(((PhotoFullscreenActivity) getActivity()).getNavigatorComponent())
                .applicationComponent(((MVVMApplication) getActivity().getApplication()).getComponent())
                .build();

        fragmentComponent.inject(this);

        if(getView() != null) {
            FragmentPhotoFullscreenBinding binding = DataBindingUtil.bind(getView());
            if(binding != null) {
                binding.setImageUtil(imageUtils.get());
                binding.setViewModel(photoFullscreenViewModel.get());
            }
            ToolbarExtensions.INSTANCE.setToolbar(this, getView().findViewById(R.id.toolbar));
        }

        if (getArguments() != null) {
            long photoId = getArguments().getLong(PHOTO_ID);
            photoFullscreenViewModel.get().setPhotoId(photoId);
            photoFullscreenViewModel.get().start();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        photoFullscreenViewModel.get().clear();
    }
}
