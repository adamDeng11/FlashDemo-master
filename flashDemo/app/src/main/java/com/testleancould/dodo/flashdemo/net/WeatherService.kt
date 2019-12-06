package com.testleancould.dodo.flashdemo.net

import com.testleancould.dodo.flashdemo.bean.WeatherBean
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

/**
 * Created by adamDeng on 2019/10/28
 * Copyright © 2019年 . All rights reserved.
 */
interface WeatherService {
    @POST("now")
    @FormUrlEncoded
    fun getNowWeather(@Field("location") location:String,@Field("key") key:String): retrofit2.Call<WeatherBean>
}