package com.sqli.mvvmapp.base

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BaseViewModel {
    protected  var compositeDisposable: CompositeDisposable = CompositeDisposable()

    protected fun add(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }

     fun clear() {
        compositeDisposable.dispose()
        compositeDisposable.clear()
    }
}