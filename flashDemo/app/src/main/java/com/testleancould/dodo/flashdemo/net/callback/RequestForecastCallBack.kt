package com.testleancould.dodo.flashdemo.net.callback

import com.testleancould.dodo.flashdemo.bean.WeatherForecast
import retrofit2.Response

/**
 * Created by adamDeng on 2019/11/11
 * Copyright © 2019年 . All rights reserved.
 */
interface RequestForecastCallBack {
    fun onResult(weatherForecastBean: Response<WeatherForecast>)
}