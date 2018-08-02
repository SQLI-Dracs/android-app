package com.sqli.mvvmapp.common.customview

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import com.sqli.mvvmapp.R
import com.sqli.mvvmapp.common.utils.ImageUtils
import dagger.Lazy
import javax.inject.Inject

class SquareImageView : android.support.v7.widget.AppCompatImageView {

    constructor(context: Context) : super(context) {
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec) //Force square
    }

}
