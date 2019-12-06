package com.adam.playcontrollerview.utils

import android.content.Context

/**
 * Created by adamDeng on 2019/11/21
 * Copyright © 2019年 . All rights reserved.
 */

/**
 *代码中写的数字类尺寸，单位都是px，而大家知道，android是要屏幕适配的，官方建议不要用px，代替的是dp
 *
 */
class DpOrPxUtils {

    //dp转px
    fun Dp2Px(context: Context,dp: Float): Float {

        var scale=context.resources.displayMetrics.density
        return (dp*scale+0.5f)
    }

    //px转dp
    fun Px2Dp(context: Context,px:Float):Float{
        var scale=context.resources.displayMetrics.density
        return (px/scale+0.5f)
    }
}