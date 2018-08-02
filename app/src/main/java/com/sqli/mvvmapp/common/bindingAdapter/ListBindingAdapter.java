package com.sqli.mvvmapp.common.bindingAdapter;

import android.databinding.BindingAdapter;
import android.support.annotation.LayoutRes;
import android.support.v4.util.Pair;
import android.support.v7.widget.RecyclerView;

import com.jaumard.recyclerviewbinding.BindableRecyclerAdapter;
import com.jaumard.recyclerviewbinding.ItemBinder;
import com.jaumard.recyclerviewbinding.RecyclerViewBindingAdapter;
import com.sqli.mvvmapp.BR;
import com.sqli.mvvmapp.common.utils.ImageUtils;

import java.util.ArrayList;
import java.util.List;

public class ListBindingAdapter {

    @BindingAdapter({"items", "itemLayout"})
    public static <T> void setRecyclerItems(RecyclerView recyclerView, List<T> items, @LayoutRes int itemLayout) {
        RecyclerViewBindingAdapter.setRecyclerItems(recyclerView, items, new ItemBinder(itemLayout, BR.data), null);
    }

    @BindingAdapter({"items", "itemLayout", "clickHandler"})
    public static <T> void setRecyclerItems(RecyclerView recyclerView, List<T> items, @LayoutRes int itemLayout,
                                            BindableRecyclerAdapter.OnClickListener<T> listener) {
        RecyclerViewBindingAdapter.setRecyclerItems(recyclerView, items, new ItemBinder(itemLayout, BR.data), listener);
    }

    @BindingAdapter({"items", "itemLayout", "clickHandler", "imageUtil"})
    public static <T> void setRecyclerItems(RecyclerView recyclerView, List<T> items, @LayoutRes int itemLayout,
                                            BindableRecyclerAdapter.OnClickListener<T> listener, ImageUtils imageUtils) {

        List<Pair<Integer, Object>> additionalData = new ArrayList<>();
        additionalData.add(new Pair<>(BR.imageUtil, imageUtils));
        ItemBinder itemBinder = new ItemBinder(BR.data, itemLayout, additionalData);

        RecyclerViewBindingAdapter.setRecyclerItems(recyclerView, items, itemBinder, listener);
    }
}
