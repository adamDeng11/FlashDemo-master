package com.adam.playcontrollerview.customview

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.ImageButton
import androidx.core.graphics.drawable.toBitmap
import com.adam.playcontrollerview.R

/**
 * Created by adamDeng on 2019/11/25
 * Copyright © 2019年 . All rights reserved.
 */
class SectorView @JvmOverloads constructor(context: Context, attrs: AttributeSet?=null, defStyle: Int=0) :
    ImageButton(context, attrs, defStyle){

    //扇形画笔
    private var sectorPaint: Paint?=null
    //音量图标
    private var bitmap: Bitmap?=null
    //取最小的宽或高
    private var whsize: Int ?=null
    private var whlength:Int?=null
    init {

        //扇形画笔
        sectorPaint= Paint()
        sectorPaint?.color= Color.GRAY
        sectorPaint?.style=Paint.Style.FILL

    }

    override fun onDraw(canvas: Canvas){
        super.onDraw(canvas)
        drawSector(canvas)
    }

    //绘制扇形
    private fun drawSector(canvas: Canvas?){

        var oval= RectF(0f,0f, whlength!!.toFloat(),whlength!!.toFloat())
        canvas?.drawArc(  oval!!, -90f, -90f, true, sectorPaint!!)
        bitmap=resources.getDrawable(R.drawable.ic_vol_min).toBitmap()
        //音量图标剪裁(这里是全部)
        var src= bitmap?.width?.let { bitmap?.height?.let { it1 -> Rect(0,0, it, it1) } }
        //音量图标位置和大小
        var dst= RectF(whsize!!/3f,whsize!!/3f, whsize!!.toFloat(),whsize!!.toFloat())
        canvas?.drawBitmap(bitmap!!,src,dst,sectorPaint)

    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {

        whsize = if (w > h) h else w
        whlength= whsize!! *2
        super.onSizeChanged(w, h, oldw, oldh)
    }
    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        return super.dispatchTouchEvent(event)
    }
    override fun onTouchEvent(event: MotionEvent): Boolean {

        when(event.action){

            MotionEvent.ACTION_DOWN -> {

                sectorPaint?.alpha = 200
                postInvalidate()


            }
            MotionEvent.ACTION_MOVE -> {


            }
            MotionEvent.ACTION_UP -> {
                sectorPaint?.alpha = 255
                postInvalidate()
            }

        }
        return super.onTouchEvent(event)
    }

}