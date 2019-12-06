package com.adam.playcontrollerview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.Button

/**
 * Created by adamDeng on 2019/11/25
 * Copyright © 2019年 . All rights reserved.
 */
class CustomButton @JvmOverloads constructor(context: Context, attrs: AttributeSet?=null, defStyle: Int=0) :
    Button(context, attrs, defStyle){
    private var paint:Paint

    init {
        paint=Paint()
        paint.color=Color.RED
        paint.strokeWidth=15f

    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        var width=width
        var height=height
        canvas!!.drawRect(0f,0f,20f,20f,paint)
    }

    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        return super.dispatchTouchEvent(event)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {

        when (event.action) {
            MotionEvent.ACTION_DOWN -> {

                paint.alpha = 100
                postInvalidate()

            }
            MotionEvent.ACTION_MOVE -> {


            }
            MotionEvent.ACTION_UP -> {
                paint.alpha=255
                postInvalidate()
            }
        }

        return super.onTouchEvent(event)
    }
}