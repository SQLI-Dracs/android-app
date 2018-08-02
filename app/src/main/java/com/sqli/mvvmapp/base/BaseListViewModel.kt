package com.sqli.mvvmapp.base

import android.databinding.ObservableField

abstract class BaseListViewModel<T> : BaseViewModel() {
    var items: ObservableField<List<T>> = ObservableField(ArrayList())

}