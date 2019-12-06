package com.adam.playcontrollerview.customview

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import kotlin.math.*

/**
 * Created by adamDeng on 2019/11/25
 * Copyright © 2019年 . All rights reserved.
 */
class ProgressView @JvmOverloads constructor(context: Context, attrs: AttributeSet?=null, defStyle: Int=0) :
    View(context, attrs, defStyle){


    private var progress = 0
    private var radius = 0f

    //文字相关
    private var mTitle = "强度 0"
    private var mTextPaint: Paint
    private var mTextBound: Rect
    private var mTextSize = 20
    private var each = 100 / 90.toDouble()


    //进度条背景1
    private var bgProPaint1:Paint
    //进度条背景画笔2
    private var bgProPaint2: Paint
    //进度条画笔
    private var progressPaint: Paint
    //进度条图标
    private var progressBitmap:Bitmap?=null




    private var centerX: Float = 0.toFloat()
    private var centerY: Float = 0.toFloat()

    private var downX = 0f
    private var downY = 0f

    private var pointPaint:Paint
    private var pointXL:Float=40f
    private var pointYL:Float?=600f

    init {

        mTextPaint = Paint()
        mTextPaint.isAntiAlias = true
        mTextPaint.strokeWidth = 4f
        mTextBound = Rect()
        mTextPaint.textSize = mTextSize.toFloat()


        //进度条背景1
        bgProPaint1 = Paint()
        bgProPaint1!!.strokeWidth = 80f
        bgProPaint1!!.style = Paint.Style.STROKE
        bgProPaint1!!.isAntiAlias = true
        bgProPaint1?.alpha=50
        bgProPaint1?.style=Paint.Style.STROKE
        bgProPaint1!!.strokeCap = Paint.Cap.ROUND

        //进度条背景画笔2
        bgProPaint2 = Paint()
        bgProPaint2!!.strokeWidth = 40f
        bgProPaint2!!.style = Paint.Style.STROKE
        bgProPaint2!!.isAntiAlias = true
        bgProPaint2?.color=Color.WHITE
        bgProPaint2?.alpha=100
        bgProPaint2!!.strokeCap = Paint.Cap.ROUND

        //进度条画笔
        progressPaint = Paint()
        progressPaint!!.strokeWidth = 40f
        progressPaint!!.style = Paint.Style.STROKE
        progressPaint!!.isAntiAlias = true
        progressPaint!!.color=Color.parseColor("#8BCEE7")
        progressPaint!!.strokeCap = Paint.Cap.ROUND

        pointPaint=Paint()
        pointPaint.color=Color.WHITE
        pointPaint.strokeWidth=40f
        pointPaint.strokeCap=Paint.Cap.ROUND

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
        val radius = centre - 40// 半径
        //画进度条背景1
        val rectF1 = RectF(
            (centre - radius).toFloat(),
            (centre - radius).toFloat(),
            (centre + radius).toFloat(),
            (centre + radius).toFloat()
        )
        canvas.drawArc(rectF1, 180f, 90f, false, bgProPaint1!!)


        //画进度条背景2
        val rectF2 = RectF(
            (centre - radius).toFloat(),
            (centre - radius).toFloat(),
            (centre + radius).toFloat(),
            (centre + radius).toFloat()
        )
        canvas.drawArc(rectF2, 185f, 80f, false, bgProPaint2!!)

        //画进度条
        val centre1 = width // 获取圆心的x坐标
        val radius1 = centre1 - 40// 半径
        val rectF = RectF(
            (centre1 - radius1).toFloat(),
            (centre1 - radius1).toFloat(),
            (centre1 + radius1).toFloat(),
            (centre1 + radius1).toFloat()
        )
        canvas.drawArc(rectF, 180f, progress.toFloat(), false, progressPaint!!)

        //画点
        canvas.drawPoint(pointXL!!,pointYL!!,pointPaint)


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
                        val y = abs(centerY - tempabsY)
                        val x = abs(centerX - tempabsX)
                        val k = y / x
                        var angle = atan(k.toDouble())



                        val radius1=width-40f
                        pointXL= radius1-cos(angle.toFloat())*radius1+40
                        pointYL= radius1-sin(angle.toFloat())*radius1+40

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