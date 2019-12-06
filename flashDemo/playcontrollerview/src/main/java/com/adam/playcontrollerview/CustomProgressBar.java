package com.adam.playcontrollerview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.ProgressBar;

import com.adam.playcontrollerview.utils.DisplayUtils;

/**
 * Created by adamDeng on 2019/11/28
 * Copyright © 2019年 . All rights reserved.
 */
public class CustomProgressBar extends ProgressBar {
    private Paint paint;
    private int circleColor;//圆的颜色
    private int circleWidth;//圆的粗细
    private int startAngle;//起始角度
    private int textSize;//文字大小
    private int textColor;//文字颜色
    private RectF rectF;//限制弧线的矩形
    private Rect bounds;//测量文字边缘

    public CustomProgressBar(Context context){
        super(context);
    }

    public CustomProgressBar(Context context, AttributeSet attributeSet){
        super(context,attributeSet);
    }

    public CustomProgressBar(Context context,AttributeSet attributeSet,int defStyleAttr){
        super(context,attributeSet,defStyleAttr);

        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attributeSet,R.styleable.CustomProgressBar,defStyleAttr,0);
        //获取圆的颜色，默认黑色
        circleColor = typedArray.getColor(R.styleable.CustomProgressBar_circleColor, Color.BLACK);
        //获取圆的粗细，默认5dp
        circleWidth = (int) typedArray.getDimension(R.styleable.CustomProgressBar_circleWidth, DisplayUtils.dp2px(context,5));
        //获取圆的起始角度，默认0度
        startAngle = typedArray.getInteger(R.styleable.CustomProgressBar_startAngle,0);
        //获取文字大小，默认18sp
        textSize = (int) typedArray.getDimension(R.styleable.CustomProgressBar_textSize,DisplayUtils.px2dp(getContext(),18));
        //获取文字颜色，默认黑色
        textColor = typedArray.getColor(R.styleable.CustomProgressBar_textColor,Color.BLACK);
        typedArray.recycle();

        paint = new Paint();
        rectF=new RectF();
        bounds=new Rect();



    }

    @Override
    protected void onMeasure(int widthMeasureSpec,int heightMeasureSpec){
        int width=MeasureSpec.getSize(widthMeasureSpec);
        int height=MeasureSpec.getSize(heightMeasureSpec);

        int widthMode=MeasureSpec.getMode(widthMeasureSpec);
        int heightMode=MeasureSpec.getMode(heightMeasureSpec);

        if (widthMode==MeasureSpec.AT_MOST){
            width=getPaddingLeft()+getPaddingRight()+DisplayUtils.dp2px(getContext(),60);

        }
        if (heightMode==MeasureSpec.AT_MOST){
            height=getPaddingTop()+getPaddingBottom()+DisplayUtils.dp2px(getContext(),60);
        }

        setMeasuredDimension(width,height);
    }

    @Override
    protected void onDraw(Canvas canvas){
        /**
         * 画圆弧
         */
        paint.setAntiAlias(true);//设置抗锯齿
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(circleWidth);
        paint.setColor(circleColor);

        rectF.set(getPaddingLeft(),getPaddingTop(),getWidth()-getPaddingRight(),getHeight()-getPaddingBottom());
        canvas.drawArc(rectF,startAngle,getProgress()*1.0f/getMax()*360,false,paint);

    }

}

