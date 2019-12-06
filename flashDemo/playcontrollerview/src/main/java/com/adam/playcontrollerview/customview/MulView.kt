package com.adam.playcontrollerview.customview

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.Button
import androidx.core.graphics.drawable.toBitmap
import com.adam.playcontrollerview.R

/**
 * Created by adamDeng on 2019/11/25
 * Copyright © 2019年 . All rights reserved.
 */
class MulView @JvmOverloads constructor(context: Context, attrs: AttributeSet?=null, defStyle: Int=0) :
    Button(context, attrs, defStyle){

    //快进
    private var forwordPaint:Paint
    private var forwordBitmap:Bitmap?=null
    //后退
    private var backPaint:Paint
    private var backBitmap:Bitmap?=null
    //暂停
    private var pausePaint:Paint
    private var pauseBitmap:Bitmap?=null



    init {

        //快进画笔
        forwordPaint= Paint()
        forwordPaint?.color= Color.WHITE
        forwordPaint?.style=Paint.Style.STROKE
        forwordPaint?.strokeWidth=110f
        forwordPaint?.alpha=100

        //暂停画笔
        pausePaint= Paint()
        pausePaint?.color=Color.BLUE
        pausePaint?.style=Paint.Style.STROKE
        pausePaint.strokeWidth=110f
        pausePaint?.alpha=100

        //后退画笔
        backPaint= Paint()
        backPaint?.color=Color.WHITE
        backPaint?.style=Paint.Style.STROKE
        backPaint?.strokeWidth=110f
        backPaint?.alpha=100

    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        forwordDraw(canvas)
        pauseDraw(canvas)
        backDraw(canvas)

    }

    //绘制快进
    private fun forwordDraw(canvas: Canvas?){

        val centre = width // 获取圆心的x坐标
        val radius = centre - 100// 半径
        //画背景
        val rectF2 = RectF(
            (centre - radius).toFloat(),
            (centre - radius).toFloat(),
            (centre + radius).toFloat(),
            (centre + radius).toFloat()
        )
        canvas!!.drawArc(rectF2, 240f, 30f, false, forwordPaint!!)

        //画图标
        forwordBitmap=resources.getDrawable(R.drawable.btn_play_forward_normal).toBitmap()
        canvas?.drawBitmap(forwordBitmap!!,width/5f*4,height/6f,forwordPaint)

    }

    //绘制暂停
    private fun pauseDraw(canvas: Canvas?){

        val centre = width // 获取圆心的x坐标
        val radius = centre - 100// 半径

        //画背景
        val rectF2 = RectF(
            (centre - radius).toFloat(),
            (centre - radius).toFloat(),
            (centre + radius).toFloat(),
            (centre + radius).toFloat()
        )
        canvas!!.drawArc(rectF2, 210f, 30f, false, pausePaint!!)

        //画图标
        pauseBitmap=resources.getDrawable(R.drawable.btn_pause_normal).toBitmap()
        canvas?.drawBitmap(pauseBitmap!!,width/5f*2,height/3f+10,pausePaint)

    }

    //绘制后退
    private fun backDraw(canvas: Canvas?){

        val centre = width // 获取圆心的x坐标
        val radius = centre - 100// 半径
        //画背景
        val rectF2 = RectF(
            (centre - radius).toFloat(),
            (centre - radius).toFloat(),
            (centre + radius).toFloat(),
            (centre + radius).toFloat()
        )
        canvas!!.drawArc(rectF2, 180f, 30f, false, backPaint!!)

        //画图标
        backBitmap=resources.getDrawable(R.drawable.btn_play_back_normal).toBitmap()
        canvas?.drawBitmap(backBitmap!!,width/6f,height/4f*3,backPaint)

    }

    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        return super.dispatchTouchEvent(event)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {

        when(event.action){

            MotionEvent.ACTION_DOWN->{
                backPaint.alpha=50
                postInvalidate()

            }
            MotionEvent.ACTION_MOVE->{

            }
            MotionEvent.ACTION_UP->{

                backPaint.alpha=100
                postInvalidate()
            }
        }

        return true
    }

}