package com.testleancould.dodo.flashdemo.ui.decoration

import android.content.Context
import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.view.View

import androidx.recyclerview.widget.OrientationHelper
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by adamDeng on 2019/10/11
 * Copyright © 2019年 深圳市云歌人工智能技术有限公司. All rights reserved.
 */
class ItemDecoration(co: Context, private val orientation: Int) : RecyclerView.ItemDecoration() {
    private val co: Context? = null
    private val dr: Drawable?
    private val sum: Int = 0
    private var top: Int = 0
    private var bottom: Int = 0
    private var left: Int = 0
    private var right: Int = 0

    init {
        dr = co.obtainStyledAttributes(attrs).getDrawable(0)
    }

    //这是垂直分割线
    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDraw(c, parent, state)


        if (OrientationHelper.HORIZONTAL == orientation) {//判断是水平还是垂直
            //绘制水平方向的分割线
            top = parent.paddingTop
            bottom = parent.height - parent.paddingBottom
            for (i in 0 until parent.childCount) {
                val childAt = parent.getChildAt(i)
                val layoutParams = childAt.layoutParams as RecyclerView.LayoutParams
                left = childAt.right + layoutParams.rightMargin
                right = left + dr!!.intrinsicWidth
                dr.setBounds(left, top, right, bottom)
                dr.draw(c)
            }
        } else {
            //绘制垂直方向的分割线
            left = parent.paddingLeft
            right = parent.width - parent.paddingRight
            for (i in 0 until parent.childCount) {
                val childAt = parent.getChildAt(i)
                val layoutParams = childAt.layoutParams as RecyclerView.LayoutParams
                top = childAt.bottom + layoutParams.bottomMargin
                bottom = top + dr!!.intrinsicHeight
                dr.setBounds(left, top, right, bottom)
                dr.draw(c)
            }
        }
    }

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        if (OrientationHelper.HORIZONTAL == orientation) {//判断是水平还是垂直
            outRect.set(0, 0, dr!!.intrinsicWidth, 0)
        } else {
            outRect.set(0, 0, 0, dr!!.intrinsicHeight)
        }
    }

    companion object {

        //采用系统内置的风格的分割线
        private val attrs = intArrayOf(android.R.attr.listDivider)
    }

}