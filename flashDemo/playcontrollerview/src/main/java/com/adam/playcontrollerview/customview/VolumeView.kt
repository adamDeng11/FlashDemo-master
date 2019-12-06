package com.adam.playcontrollerview.customview

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.widget.ImageButton
import androidx.core.graphics.drawable.toBitmap
import com.adam.playcontrollerview.R
import kotlin.math.*

/**
 * Created by adamDeng on 2019/11/25
 * Copyright © 2019年 . All rights reserved.
 */
class VolumeView @JvmOverloads constructor(context: Context, attrs: AttributeSet?=null, defStyle: Int=0) :
    ImageButton(context, attrs, defStyle){

    private var progress = 0
    private var radius = 0f

    //文字相关
    private var mTitle = "强度 0"
    private var mTextPaint: Paint
    private var mTextBound: Rect
    private var mTextSize = 20
    private var each = 100 / 90.toDouble()

    //音量背景画笔
    private var bgVolumePaint: Paint
    //音量进度画笔
    private var volumePaint: Paint
    //音量进度图标
    private var bitmapVolume: Bitmap?=null


    private var centerX: Float = 0.toFloat()
    private var centerY: Float = 0.toFloat()

    private var downX = 0f
    private var downY = 0f


    init {

        mTextPaint = Paint()
        mTextPaint.isAntiAlias = true
        mTextPaint.strokeWidth = 4f
        mTextBound = Rect()
        mTextPaint.textSize = mTextSize.toFloat()

        //音量进度背景画笔
        bgVolumePaint= Paint()
        bgVolumePaint.color= Color.RED
        bgVolumePaint.style=Paint.Style.STROKE
        bgVolumePaint.strokeWidth=100f
        //音量进度画笔
        volumePaint= Paint()
        volumePaint.color= Color.GREEN
        volumePaint.style=Paint.Style.STROKE
        volumePaint.strokeWidth=60f
        volumePaint.alpha=200

    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        // 计算了描绘字体需要的范围
        mTextPaint.getTextBounds(mTitle, 0, mTitle.length, mTextBound)
        canvas.drawText(mTitle, width / 2 - mTextBound.width() * 1.0f / 2, (height / 2).toFloat(), mTextPaint)
        centerX = width.toFloat()
        centerY = height.toFloat()
        radius = centerX - 40// 半径

        drawProgress(canvas)

    }


    //绘制进度条
    private fun drawProgress(canvas: Canvas) {

        val centre = width // 获取圆心的x坐标
        val radius = centre - 60// 半径

        //画进度条背景
        val rectF2 = RectF(
            (centre - radius).toFloat(),
            (centre - radius).toFloat(),
            (centre + radius).toFloat(),
            (centre + radius).toFloat()
        )
        canvas.drawArc(rectF2, 180f, 90f, false, bgVolumePaint!!)

        //绘制音量进度图标
        bitmapVolume=resources.getDrawable(R.drawable.ic_vol_indi_bar_right).toBitmap()
        var src= bitmapVolume?.width?.let { bitmapVolume?.height?.let { it1 -> Rect(0,0, it, it1) } }
        var dst= RectF(width/5f,height/5f, width.toFloat(),  height.toFloat())
        canvas?.drawBitmap(bitmapVolume!!,src,dst,bgVolumePaint)

        //画进度条
        val centre1 = width // 获取圆心的x坐标
        val radius1 = centre1 - 80// 半径
        val rectF = RectF(
            (centre1 - radius1).toFloat(),
            (centre1 - radius1).toFloat(),
            (centre1 + radius1).toFloat(),
            (centre1 + radius1).toFloat()
        )
        canvas.drawArc(rectF, 180f, progress.toFloat(), false, volumePaint!!)

    }

    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        return super.dispatchTouchEvent(event)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                downX = event.x
                downY = event.y

            }
            MotionEvent.ACTION_MOVE -> {
                val tempX = event.x
                val tempY = event.y

                if (tempY < centerY + 20 && tempX < centerX || tempY < centerY) {
                    //90度以前切且0度以下
                    if (tempY < centerY + 20 && centerY < tempY && tempX < centerX) {
                        progress = 0

                        mTitle = "强度 0"
                        postInvalidate()
                    }
                    val tempabsX = abs(tempX)
                    val tempabsY = abs(tempY)
                    var distance =
                        sqrt(((centerX - tempabsX) * (centerX - tempabsX) + (centerY - tempabsY) * (centerY - tempabsY)).toDouble())

                    distance -= radius.toDouble()

                    if (distance < 40 && distance > -10) {
                        //progress=40;
                        val y = Math.abs(centerY - tempabsY)
                        val x = Math.abs(centerX - tempabsX)
                        val k = y / x
                        var angle = atan(k.toDouble())


                        angle /= 0.017

                        Log.i("独角",angle.toString())

                        if (tempX > centerX) {
                            angle = 180 - angle

                        }
                        progress = angle.toInt()
                        if (angle > 89) {
                            progress = 90
                        }

                        mTitle = "强度 " + Math.round(progress * each)

                        postInvalidate()
                    }
                }
            }
            MotionEvent.ACTION_UP -> {
            }
        }
        return true
    }

}