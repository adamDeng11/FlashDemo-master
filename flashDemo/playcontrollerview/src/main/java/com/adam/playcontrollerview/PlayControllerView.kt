package com.adam.playcontrollerview

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import androidx.core.graphics.drawable.toBitmap
import com.adam.playcontrollerview.utils.DpOrPxUtils
import kotlin.math.abs
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

/**
 * Created by adamDeng on 2019/11/20
 * Copyright © 2019年 . All rights reserved.
 */
class PlayControllerView @JvmOverloads constructor(context: Context,attrs:AttributeSet?=null,defStyleAttr:Int=0):View(context,attrs,defStyleAttr){

    //初始化尺寸转换
    private var dpOrPxUtils:DpOrPxUtils= DpOrPxUtils()

    private val mContext:Context=context
    //画笔
    private var paint:Paint?=null
    //背景画笔
    private var bgPaint:Paint?=null
    //矩形
    private var oval: RectF?=null

    //音量图标
    private var bitmap:Bitmap?=null
    //音量进度图标
    private var bitmapVolume:Bitmap?=null

    //进度条背景1
    private var bgProPaint1:Paint?=null
    //进度条背景2
    private var bgProPaint2:Paint?=null
    //进度条画笔
    private var progressPaint:Paint
    //进度条图标
    private var progressBitmap:Bitmap?=null
    //进度条的角度
    private var angle:Float=180/12f

    //判断是否按下
    private var isDown:Boolean?=null

    //触摸点xy坐标
    internal var x: Float = 0.toFloat()
    internal var y: Float = 0.toFloat()

    private var cx: Int?=null
    private var cy:Int?=null
    private var whsize: Int ?=null

    init {

        //进度条背景1画笔
        bgProPaint1= Paint()
        bgProPaint1?.color=Color.WHITE
        bgProPaint1?.alpha=50
        bgProPaint1?.style=Paint.Style.STROKE
        bgProPaint1?.strokeWidth=80f
        //进度条背景2画笔
        bgProPaint2= Paint()
        bgProPaint2?.color=Color.WHITE
        bgProPaint2?.alpha=100
        bgProPaint2?.style=Paint.Style.STROKE
        //设置圆角画笔
        bgProPaint2?.strokeCap=Paint.Cap.ROUND
        bgProPaint2?.strokeWidth=40f
        //进度条画笔
        progressPaint= Paint()
        progressPaint?.color=Color.parseColor("#8BCEE7")
        progressPaint?.strokeCap=Paint.Cap.ROUND
        progressPaint?.style=Paint.Style.STROKE
        progressPaint?.strokeWidth=40f

    }



    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        drawSector(canvas)
        drawVolume(canvas)
        drawPause(canvas)
        drawProgress(canvas)

        if (isDown==true)
        {
            drawmap(canvas)
        } else
        {
            drawbit(canvas)
        }

    }

    /** 按下弹起后画点位置 **/
    private fun drawbit(canvas: Canvas?)
    {
        var oval= RectF(width/14f,height/14f, width.toFloat()+width/14*13,  height.toFloat()+height/14*13)
        //画进度
        canvas?.drawArc(oval, -180f,  (angle * count), false, progressPaint)
    }

    /** 画 **/
    private fun drawmap(canvas:Canvas?)
    {
        var oval= RectF(width/14f,height/14f, width.toFloat()+width/14*13,  height.toFloat()+height/14*13)
        if (temp < 0)//判断是否超过90度
        {
            canvas?.drawArc(oval, -180f, 180 + temp, false, progressPaint)
            var a = (180 + temp)
        } else
        {
            canvas?.drawArc(oval, -180f, temp, false, progressPaint)
        }
    }

    //画扇形和音量图标
   @Suppress("DEPRECATION")
   private fun drawSector(canvas: Canvas?){
        canvas?.drawColor(Color.RED)
        paint= Paint()
        /*oval= RectF(dpOrPxUtils.Dp2Px(context,width/2f),dpOrPxUtils.Dp2Px(context,height/2f),dpOrPxUtils.Dp2Px(context,width.toFloat()+width/2f),dpOrPxUtils.Dp2Px(context,height.toFloat()+height/2f))*/
        oval= RectF(width/2f,height/2f, width.toFloat()+width/2f,height.toFloat()+height/2f)
        paint?.color=Color.GRAY
        paint?.style=Paint.Style.FILL
        canvas?.drawArc(  oval!!, -90f, -90f, true, paint!!)

        bitmap=resources.getDrawable(R.drawable.ic_vol_min).toBitmap()
        //音量图标剪裁(这里是全部)
        var src= bitmap?.width?.let { bitmap?.height?.let { it1 -> Rect(0,0, it, it1) } }
        //音量图标位置和大小
        var dst=RectF(width/4f*3,height/4f*3, width.toFloat(),  height.toFloat())
        canvas?.drawBitmap(bitmap!!,src,dst,paint)

    }

    /*bitmap对象只能初始化一次，有多个需要声明多个*/

    //绘制音量进度条
    @Suppress("DEPRECATION")
    private fun drawVolume(canvas: Canvas?){
        //绘制音量进度背景
        bgPaint= Paint()
        oval= RectF(width/2f,height/2f, width.toFloat()+width/2f,  height.toFloat()+height/2f)
        bgPaint?.color=Color.RED
        bgPaint?.style=Paint.Style.STROKE
        bgPaint?.strokeWidth=100f
        canvas?.drawArc(oval!!, -90f, -90f, false, bgPaint!!)

        //绘制音量进度图标
        bitmapVolume=resources.getDrawable(R.drawable.ic_vol_indi_bar_right).toBitmap()
        var src= bitmapVolume?.width?.let { bitmapVolume?.height?.let { it1 -> Rect(0,0, it, it1) } }
        var dst=RectF(width/4f*2,height/4f*2, width.toFloat(),  height.toFloat())
        canvas?.drawBitmap(bitmapVolume!!,src,dst,paint)

        //绘制音量进度
        paint= Paint()
        /**
         *记得动态
         */
        oval= RectF(width/2f+10,height/2f+10, width.toFloat()+width/2f+10,  height.toFloat()+height/2f+10)
        paint?.color=Color.GREEN
        paint?.style=Paint.Style.STROKE
        paint?.strokeWidth=70f
        paint?.alpha=200
        canvas?.drawArc(oval!!, -150f, -90f, false, paint!!)

    }

    //绘制快进、暂停、播放、后退
    private fun drawPause(canvas: Canvas?){
        paint= Paint()
        oval= RectF(width/7f*2,height/7f*2, width.toFloat()+width/7*5,  height.toFloat()+height/7*5)
        paint?.color=Color.YELLOW
        paint?.style=Paint.Style.STROKE
        paint?.strokeWidth=150f
        canvas?.drawArc(oval!!, -90f, -90f, false, paint!!)

    }

    //绘制进度条大小
    @Suppress("DEPRECATION")
    private fun drawProgress(canvas: Canvas?){

        //绘制进度条背景1
        oval=RectF(width/14f,height/14f, width.toFloat()+width/14*13,  height.toFloat()+height/14*13)
        canvas?.drawArc(oval!!, -90f, -90f, false, bgProPaint1!!)

        //绘制进度条背景2
        oval=RectF(width/14f,height/14f, width.toFloat()+width/14*13,  height.toFloat()+height/14*13)
        canvas?.drawArc(oval!!, -95f, -90f, false, bgProPaint2!!)

        //绘制进度条
        oval= RectF(width/14f,height/14f, width.toFloat()+width/14*13,  height.toFloat()+height/14*13)
        canvas?.drawArc(oval!!, -180f, 90f, false, progressPaint!!)

        //绘制进度条圆角图标
        progressBitmap=resources.getDrawable(R.drawable.btn_thumb_normal).toBitmap()
        //剪裁图标全部
        var src= progressBitmap?.width?.let { progressBitmap?.height?.let { it1 -> Rect(0,0, it, it1) } }
        /**
         *图标大小位置，记得
         */
        var dst=RectF(150f,150f, width.toFloat()-350,  height.toFloat()-350)
        /*canvas?.drawBitmap(progressBitmap!!,src, dst, paint)*/

    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        /*width = w
        height = h*/
        whsize = if (w > h) h else w
        cx = width
        cy = height
        //getsize();
        super.onSizeChanged(w, h, oldw, oldh)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {

        when(event?.action){
            MotionEvent.ACTION_DOWN->{
                x=event.x
                y=event.y
                if (isNearByProgress(x,y)){
                    isDown=true
                    move(getx(x, y), gety(x, y))
                }else{
                    isDown=false
                }
            }
            MotionEvent.ACTION_MOVE->{
                x=event.x
                y=event.y
                if (isDown==true){
                    move(getx(x, y), gety(x, y))
                }

            }
            MotionEvent.ACTION_UP->{
                x=event.x
                y=event.y

                if (isDown==true){
                    isDown=false
                    getposition(getx(x, y), gety(x, y))
                }
            }

        }


        return true
    }

    /** 在第几个刻度上  */
    internal var count = 0

    /** 判断是否在刻度上  */
    private fun getposition(getx: Float, gety: Float) {
        /** 小圆在轨道上的 弧度  */
        var mangle = 0f

        point.x = getx.toInt()
        point.y = gety.toInt()
        if (temp > 0) {
            mangle = temp
        } else {
            mangle = 90 + temp
        }
        if (mangle % angle > 5) {
            count = (mangle / angle + 1).toInt()
        } else {
            count = (mangle / angle).toInt()
        }
        invalidate()
    }

    internal var point = Point()

    /** 移动，进度  */
    private fun move(x: Float, y: Float) {
        point.x = x.toInt()
        point.y = y.toInt()

        if (x >= width/14f && y >= whsize!! - width/14f) {
            return
        }
        invalidate()
    }


    /** 保存点的角度  */
    private var temp = 0f

    /** 获取角度  */
    private fun getxy(x: Float, y: Float): Float {

        val ao = (Math.atan(((cy !!- y) / (cx !!- x)).toDouble()) / Math.PI * 90).toFloat()
        return ao
    }

    private fun getx(x: Float, y: Float): Float {

        temp = getxy(x, y)
        return if (getxy(x, y) < 0) {
            (cx !!+(whsize  !!- width/14f) * cos(
                getxy(
                    x,
                    y
                ) * Math.PI / 90
            )).toFloat()
        } else (cx !!- (whsize  !!- width/14f) * cos(
            getxy(
                x,
                y
            ) * Math.PI / 90
        )).toFloat()
    }

    private fun gety(x: Float, y: Float): Float {

        return if (getxy(x, y) < 0) {
            ((whsize  !!- width/14f * sin(getxy(x, y) * Math.PI / 90) + this!!.cy!!).toFloat())
        } else (cy !!- (whsize !!-width/14f) * sin(
            getxy(
                x,
                y
            ) * Math.PI / 90
        )).toFloat()

    }

    /** 判断是否在圆环边上>20*/
    private fun isNearByProgress(x:Float,y:Float): Boolean {
        var nearDis= abs(width.toFloat()-width/14*13) - sqrt(((x - width) * (x -width) + (y - height) * (y - height)).toDouble())
        Log.i("adam",nearDis.toString())
        var isNear = nearDis<15
        return isNear
    }


}