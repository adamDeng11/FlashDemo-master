package com.adam.weatherview.util;

import android.content.Context;

/**
 * Created by adamDeng on 2019/11/8
 * Copyright © 2019年 . All rights reserved.
 */
public class DisplayUtils {
    public static int dp2px(Context context, float dpValue)
    {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

}
