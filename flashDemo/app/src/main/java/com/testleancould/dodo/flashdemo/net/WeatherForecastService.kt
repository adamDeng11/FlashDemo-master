package com.testleancould.dodo.flashdemo.net

import com.testleancould.dodo.flashdemo.bean.WeatherForecast
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by adamDeng on 2019/11/11
 * Copyright © 2019年 . All rights reserved.
 */
interface WeatherForecastService {
    @GET("forecast")
    fun getWeatherForecast(@Query("location") location: String,@Query("key") key:String): retrofit2.Call<WeatherForecast>
}