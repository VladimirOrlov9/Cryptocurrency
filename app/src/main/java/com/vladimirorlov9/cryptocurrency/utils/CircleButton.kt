package com.vladimirorlov9.cryptocurrency.utils

import android.content.Context
import android.util.AttributeSet

class CircleButton constructor(context: Context, attributeSet: AttributeSet, defStyle: Int) :
    androidx.appcompat.widget.AppCompatButton(context, attributeSet, defStyle) {


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        setMeasuredDimension(widthMeasureSpec, widthMeasureSpec)
    }
}