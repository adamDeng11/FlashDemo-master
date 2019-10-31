package com.testleancould.dodo.flashdemo.net.callback

import com.testleancould.dodo.flashdemo.bean.WeatherBean
import retrofit2.Response

/**
 * Created by adamDeng on 2019/10/31
 * Copyright © 2019年 . All rights reserved.
 */
interface RequestCallback{
    fun onResult(weatherBean: Response<WeatherBean>)
}