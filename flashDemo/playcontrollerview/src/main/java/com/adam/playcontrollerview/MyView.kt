package com.adam.playcontrollerview

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import androidx.core.graphics.drawable.toBitmap
import com.adam.playcontrollerview.listener.*
import com.adam.playcontrollerview.utils.DisplayUtils
import kotlin.math.*

/**
 * Created by adamDeng on 2019/11/21
 * Copyright © 2019年 . All rights reserved.
 */
class MyView  @JvmOverloads constructor(context: Context, attrs: AttributeSet?=null, defStyle: Int=0) :
    View(context, attrs, defStyle) {


    //进度条角度
    private var progress = 0
    private var radius = 0f
    //音量进度角度
    private var volumeProgress=0
    private var volumeRadius=0f
    //指针图标角度
    private var volumeArrow=0f

    //文字相关
    private var mTitle = "强度 0"
    private var mTextPaint: Paint= Paint()
    private var mTextBound: Rect
    private var mTextSize = 40
    /**
     * 90度分成一百份
     */
    private var each = 100 / 90.toDouble()
    private var eachVolume=100/90.toDouble()

    //扇形画笔
    private var sectorPaint:Paint= Paint()
    //音量图标
    private var bitmap: Bitmap?=null

    //音量背景画笔
    private var bgVolumePaint:Paint=Paint()
    //音量进度画笔
    private var volumePaint:Paint=Paint()
    //音量进度图标
    private var bitmapVolume: Bitmap?=null
    //音量指针图标
    private var volumePointBitmap:Bitmap?=null

    //快进
    private var forwordPaint:Paint=Paint()
    private var forwordBitmap:Bitmap?=null
    //后退
    private var backPaint:Paint=Paint()
    private var backBitmap:Bitmap?=null
    //暂停
    private var pausePaint:Paint=Paint()
    private var pauseBitmap:Bitmap?=null

    //进度条背景1
    private var bgProPaint1:Paint=Paint()
    //进度条背景画笔2
    private var bgProPaint2: Paint=Paint()
    //进度条画笔
    private var progressPaint: Paint=Paint()

    private var centerX: Float = 0.toFloat()
    private var centerY: Float = 0.toFloat()

    /**
     * 触摸点的位置
     */
    private var downX = 0f
    private var downY = 0f

    /**
     * 进度条点的x，y
     */
    private var pointPaint:Paint= Paint()
    private var pointXL:Float=140f
    private var pointYL:Float=580f

    /**
     * 音量指针的x，y
     */
    private var arrowXL:Float=300f
    private var arrowYL:Float=575f

    private var mDown: OnDownActionListener?=null
    private var mBackDown: OnBackDownActionListener?=null
    private var mPauseDown:OnPauseDownActionListener?=null
    private var mForwardDown:OnForwardDownActionListener?=null
    private var mVolumeChange:OnVolumeListener?=null

    init {

        mTextPaint.isAntiAlias = true
        mTextPaint.strokeWidth = 4f
        mTextBound = Rect()
        mTextPaint.textSize = mTextSize.toFloat()

        //扇形画笔
        sectorPaint.color=Color.GRAY
        sectorPaint.style=Paint.Style.FILL
        bitmap=resources.getDrawable(R.drawable.ic_vol_mute).toBitmap()

        //音量进度背景画笔
        bgVolumePaint.color=Color.parseColor("#7713C0")
        bgVolumePaint.style=Paint.Style.STROKE
        bgVolumePaint.strokeWidth=100f
        //音量进度画笔
        volumePaint.color=Color.GREEN
        volumePaint.style=Paint.Style.STROKE
        volumePaint.strokeWidth=70f
        volumePaint.alpha=200

        //快进画笔
        forwordPaint.color = Color.WHITE
        forwordPaint.style=Paint.Style.STROKE
        forwordPaint.strokeWidth=110f
        forwordPaint.alpha=100

        //暂停画笔
        pausePaint.color=Color.BLUE
        pausePaint.style=Paint.Style.STROKE
        pausePaint.strokeWidth=110f
        pausePaint.alpha=100

        //后退画笔
        backPaint.color=Color.WHITE
        backPaint.style=Paint.Style.STROKE
        backPaint.strokeWidth=110f
        backPaint.alpha=100

        //进度条背景1
        bgProPaint1.strokeWidth = 90f
        bgProPaint1.style = Paint.Style.STROKE
        bgProPaint1.isAntiAlias = true
        bgProPaint1.alpha=50
        bgProPaint1.style=Paint.Style.STROKE
        bgProPaint1.strokeCap = Paint.Cap.ROUND

        //进度条背景画笔2
        bgProPaint2.strokeWidth = 40f
        bgProPaint2.style = Paint.Style.STROKE
        bgProPaint2.isAntiAlias = true
        bgProPaint2.color=Color.WHITE
        bgProPaint2.alpha=100
        bgProPaint2.strokeCap = Paint.Cap.ROUND

        //进度条画笔
        progressPaint.strokeWidth = 40f
        progressPaint.style = Paint.Style.STROKE
        progressPaint.isAntiAlias = true
        progressPaint.color=Color.parseColor("#8BCEE7")
        progressPaint.strokeCap = Paint.Cap.ROUND

        pointPaint.color=Color.WHITE
        pointPaint.strokeWidth=40f
        pointPaint.strokeCap=Paint.Cap.ROUND

    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        var width=MeasureSpec.getSize(widthMeasureSpec)
        var height=MeasureSpec.getSize(heightMeasureSpec)

        var widthMode=MeasureSpec.getMode(widthMeasureSpec)
        var heightMode=MeasureSpec.getMode(heightMeasureSpec)

        if (widthMode==MeasureSpec.AT_MOST){
            width=paddingLeft+paddingRight+DisplayUtils.dp2px(context, 200F)
            height=paddingTop+paddingBottom+DisplayUtils.dp2px(context,200F)
        }

        setMeasuredDimension(width,height)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        /**
         * 计算了描绘字体需要的范围
         */

        centerX = width.toFloat()
        centerY = height.toFloat()
        radius = centerX - 140// 半径
        volumeRadius=centerX/2-10

        titleDraw(canvas)
        sectorDraw(canvas)
        volumeDraw(canvas)
        volumeArrowDraw(canvas)
        forwardDraw(canvas)
        pauseDraw(canvas)
        backDraw(canvas)
        progressDraw(canvas)

    }

    /**
     * 绘制文字
     */
    private fun titleDraw(canvas: Canvas){
        mTextPaint.getTextBounds(mTitle, 0, mTitle.length, mTextBound)
        canvas.drawText(mTitle, 0f , (height / 3).toFloat(), mTextPaint)
    }


    /**
     * 绘制扇形
     */
    private fun sectorDraw(canvas: Canvas?){

        var oval= RectF(width/3f*2,height/3f*2, width.toFloat()+width/3f,height.toFloat()+height/3f)
        canvas?.drawArc(  oval, -90f, -90f, true, sectorPaint)

        //音量图标剪裁(这里是全部)
        var src= bitmap?.width?.let { bitmap?.height?.let { it1 -> Rect(0,0, it, it1) } }
        //音量图标位置和大小
        var dst=RectF(width/5f*4,height/5f*4, width.toFloat(),  height.toFloat())
        canvas?.drawBitmap(bitmap!!,src,dst,sectorPaint)

    }

    /**
     * 绘制音量
     */
    private fun volumeDraw(canvas: Canvas?){

        //绘制音量进度背景
        var oval= RectF(width/2f+50,height/2f+50, width.toFloat()+width/2f-50,  height.toFloat()+height/2f-50)
        canvas?.drawArc(oval, -90f, -90f, false, bgVolumePaint)

        //绘制音量进度图标
        bitmapVolume=resources.getDrawable(R.drawable.ic_vol_indi_bar_right).toBitmap()
        var src= bitmapVolume?.width?.let { bitmapVolume?.height?.let { it1 -> Rect(0,0, it, it1) } }
        var dst=RectF(width/4f*2+50,height/4f*2+50, width.toFloat(),  height.toFloat())
        canvas?.drawBitmap(bitmapVolume!!,src,dst,bgVolumePaint)

        //绘制音量进度
        oval= RectF(width/2f+65,height/2f+65, width.toFloat()+width/2f-65,  height.toFloat()+height/2f-65)
        canvas?.drawArc(oval, 180f, volumeProgress.toFloat(), false, volumePaint)

    }

    /**
     * 绘制音量指针图标
     */
    private fun volumeArrowDraw(canvas: Canvas?){
        canvas!!.save()
        volumePointBitmap=resources.getDrawable(R.drawable.ic_vol_arrow_normal).toBitmap()
        var arrowSrc= volumePointBitmap?.width?.let { volumePointBitmap?.height?.let { it1 -> Rect(0,0, it, it1) } }
        var arrowDst=RectF(arrowXL,arrowYL,arrowXL+50,arrowYL+50)
        canvas.rotate(volumeArrow,arrowDst.left+(arrowDst.right-arrowDst.left)/2,arrowDst.top+(arrowDst.bottom-arrowDst.top)/2)//根据角度旋转
        canvas.drawBitmap(volumePointBitmap!!,arrowSrc,arrowDst,bgVolumePaint)
        canvas.restore()
    }

    /**
     * 绘制快进
     */
    private fun forwardDraw(canvas: Canvas?){

        var oval= RectF(width/7f*3-11,height/7f*3-11, width.toFloat()+width/7*4+11,  height.toFloat()+height/7*4+11)
        canvas!!.drawArc(oval, 240f, 30f, false, forwordPaint)

        //画图标
        forwordBitmap=resources.getDrawable(R.drawable.btn_play_forward_normal).toBitmap()
        canvas?.drawBitmap(forwordBitmap!!,width/5f*4,height/5f*2,forwordPaint)
    }
    /**
     * 绘制暂停、播放
     */
    private fun pauseDraw(canvas: Canvas?){

        var oval= RectF(width/7f*3-11,height/7f*3-11, width.toFloat()+width/7*4+11,  height.toFloat()+height/7*4+11)
        canvas?.drawArc(oval!!, 210f, 30f, false, pausePaint)

        //画图标
        pauseBitmap=resources.getDrawable(R.drawable.btn_pause_normal).toBitmap()
        canvas?.drawBitmap(pauseBitmap!!,width/8f*4,height/9f*5,pausePaint)

    }

    /**
     * 绘制后退按钮
     */
    private fun backDraw(canvas: Canvas?){

        var oval= RectF(width/7f*3-11,height/7f*3-11, width.toFloat()+width/7*4+11,  height.toFloat()+height/7*4+11)
        canvas!!.drawArc(oval, 180f, 30f, false, backPaint)

        //画图标
        backBitmap=resources.getDrawable(R.drawable.btn_play_back_normal).toBitmap()
        canvas?.drawBitmap(backBitmap!!,width/8f*3,height/5f*4,backPaint)
    }

    /**
     * 绘制进度条
     */
    private fun progressDraw(canvas: Canvas) {

        val centre1 = width // 获取圆心的x坐标
        val radius1 = centre1 - 130// 半径
        //画进度条背景1
        val rectF1 = RectF(
            (centre1 - radius1).toFloat(),
            (centre1 - radius1).toFloat(),
            (centre1 + radius1).toFloat(),
            (centre1 + radius1).toFloat()
        )
        canvas.drawArc(rectF1, 180f, 90f, false, bgProPaint1)


        val centre2 = width // 获取圆心的x坐标
        val radius2 = centre2 - 140// 半
        //画进度条背景2
        val rectF2 = RectF(
            (centre2 - radius2).toFloat(),
            (centre2 - radius2).toFloat(),
            (centre2 + radius2-70).toFloat(),
            (centre2 + radius2-70).toFloat()
        )
        canvas.drawArc(rectF2, 180f, 90f, false, bgProPaint2)

        //画进度条
        val centre = width // 获取圆心的x坐标
        val radius = centre - 140// 半径
        val rectF = RectF(
            (centre - radius).toFloat(),
            (centre - radius).toFloat(),
            (centre + radius-70).toFloat(),
            (centre + radius-70).toFloat()
        )
        canvas.drawArc(rectF, 180f, progress.toFloat(), false, progressPaint)

        canvas.drawPoint(pointXL,pointYL,pointPaint)

    }

    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        return super.dispatchTouchEvent(event)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                downX = event.x
                downY = event.y

                val tempabsX = abs(downX)
                val tempabsY = abs(downY)

                var distance=sqrt(((width - tempabsX) * (width - tempabsX) + (height - tempabsY) * (height - tempabsY)).toDouble())
                if (distance<width/3f){
                    sectorPaint.alpha=100
                    if (mDown!=null){
                        mDown?.onDown(downX.toInt(),downY.toInt())
                    }

                }

                if (distance<width/7*5&&distance>width/4f*2){

                    var angle= atan((height-tempabsY)/(width-tempabsX))


                    when{
                        angle>0&&angle<0.5->{
                            backPaint.alpha=50
                            mBackDown?.onDown(downX.toInt(),downY.toInt())
                        }
                        angle>0.5&&angle<1->{
                            pausePaint.alpha=50
                            mPauseDown?.onDown(downX.toInt(),downY.toInt())}
                        angle>1&&angle<1.5->{
                            forwordPaint.alpha=50
                            mForwardDown?.onDown(downX.toInt(),downY.toInt())
                        }
                    }



                }
                postInvalidate()

            }
            MotionEvent.ACTION_MOVE -> {
                val tempX = event.x
                val tempY = event.y

                if (tempY < centerY + 20 && tempX < centerX || tempY < centerY) {
                    //90度以前切且0度以下
                    if (tempY < centerY + 20 && centerY < tempY && tempX < centerX) {

                        postInvalidate()
                    }
                    val tempabsX = abs(tempX)
                    val tempabsY = abs(tempY)
                    var distance =
                        sqrt(((centerX - tempabsX) * (centerX - tempabsX) + (centerY - tempabsY) * (centerY - tempabsY)).toDouble())

                    distance -= volumeRadius.toDouble()

                    if (distance < 40 && distance > -10) {
                        //progress=40;
                        val y = abs(centerY - tempabsY)
                        val x = abs(centerX - tempabsX)
                        val k = y / x
                        var angle = atan(k.toDouble())

                        val radius1=width-320f
                        arrowXL= radius1-cos(angle.toFloat())*radius1+300
                        arrowYL= radius1-sin(angle.toFloat())*radius1+300

                        angle /= 0.017
                        volumeArrow=angle.toFloat()


                        if (tempX > centerX) {
                            angle = 180 - angle

                        }
                        volumeProgress = angle.toInt()

                        //滑动的角度改变扇形图标
                        if (volumeProgress==0) {
                            bitmap=resources.getDrawable(R.drawable.ic_vol_mute).toBitmap()
                        }
                        if (volumeProgress>0) {
                            bitmap=resources.getDrawable(R.drawable.ic_vol_min).toBitmap()
                        }
                        if (volumeProgress>30) {
                            bitmap=resources.getDrawable(R.drawable.ic_vol_middle).toBitmap()
                        }
                        if (volumeProgress>60){
                            bitmap=resources.getDrawable(R.drawable.ic_vol_max).toBitmap()
                        }


                        if (angle > 89) {
                            volumeProgress = 90
                            volumeArrow=90f
                        }

                        mVolumeChange?.onVolumeChange(round(volumeProgress * eachVolume).toInt())

                        postInvalidate()
                    }
                }

                if (tempY < centerY + 20 && tempX < centerX || tempY < centerY) {
                    //90度以前切且0度以下
                    if (tempY < centerY + 20 && centerY < tempY && tempX < centerX) {

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

                            val radius1=width-170f
                            pointXL= radius1-cos(angle.toFloat())*radius1+140
                            pointYL= radius1-sin(angle.toFloat())*radius1+140

                        angle /= 0.017

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
                downX = event.x
                downY = event.y

                val tempabsX = abs(downX)
                val tempabsY = abs(downY)
                var distance=sqrt(((width - tempabsX) * (width - tempabsX) + (height - tempabsY) * (height - tempabsY)).toDouble())
                if (distance<width/3f&&distance>0){
                    sectorPaint!!.alpha=255

                }
                if (distance<width/7*5&&distance>width/4f*2){

                    var angle= atan((height-tempabsY)/(width-tempabsX))
                    when{
                        angle>0&&angle<0.5->{backPaint.alpha=100}
                        angle>0.5&&angle<1->{pausePaint.alpha=100}
                        angle>1&&angle<1.5->{forwordPaint.alpha=100}
                    }

                }
                postInvalidate()
            }
        }
        return true
    }


    fun setOnDownActionListener(onDownActionListener: OnDownActionListener){

        mDown=onDownActionListener
    }

    fun setOnBackDownActionListener(onBackDownActionListener: OnBackDownActionListener){

        mBackDown=onBackDownActionListener
    }
    fun setOnPauseDownActionListener(onPauseDownActionListener: OnPauseDownActionListener){

        mPauseDown=onPauseDownActionListener
    }
    fun setOnForwardDownActionListener(onForwardDownActionListener: OnForwardDownActionListener){

        mForwardDown=onForwardDownActionListener
    }

    fun setOnVolumeListener(onVolumeListener: OnVolumeListener){

        mVolumeChange=onVolumeListener
    }


}

