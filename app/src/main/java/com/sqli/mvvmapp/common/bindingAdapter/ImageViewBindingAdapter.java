package com.sqli.mvvmapp.common.bindingAdapter;

import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.sqli.mvvmapp.common.customview.SquareImageView;
import com.sqli.mvvmapp.common.utils.ImageUtils;

public class ImageViewBindingAdapter {

    @BindingAdapter({"bind:img_url", "bind:image_utils"})
    public static void setImage(ImageView image, String url, ImageUtils imageUtils) {
        imageUtils.loadImageInto(image, url);
    }
}
