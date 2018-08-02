package com.sqli.mvvmapp.common.bindingAdapter

import android.databinding.BindingAdapter
import android.support.v4.view.ViewPager
import com.sqli.mvvmapp.mvvm.user.view.UserDetailViewPagerAdapter

class ViewPagerBindingAdapter {
    companion object {

        @BindingAdapter("adapter", "onPageChangeListener")
        @JvmStatic
        fun ViewPager.setAdapter(adapter: UserDetailViewPagerAdapter, onPageChangeListener: ViewPager.OnPageChangeListener) {
            this.adapter = adapter
            this.addOnPageChangeListener(onPageChangeListener)
        }
    }

}