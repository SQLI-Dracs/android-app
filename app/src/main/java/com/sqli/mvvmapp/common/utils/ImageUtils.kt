package com.sqli.mvvmapp.common.utils

import android.widget.ImageView
import com.sqli.mvvmapp.R
import com.squareup.picasso.Picasso
import javax.inject.Inject

class ImageUtils @Inject constructor(){
    fun loadImageInto(imageView: ImageView, url: String?) {

        Picasso.get()
                .load(url)
                .placeholder(R.color.white_translucid)
                .error(R.color.colorPrimaryDark)
                .into(imageView)

    }
}