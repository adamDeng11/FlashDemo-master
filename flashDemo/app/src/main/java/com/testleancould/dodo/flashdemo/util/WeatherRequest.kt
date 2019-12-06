package com.testleancould.dodo.flashdemo.util

import android.util.Log
import com.testleancould.dodo.flashdemo.bean.WeatherBean
import com.testleancould.dodo.flashdemo.bean.WeatherForecast
import com.testleancould.dodo.flashdemo.net.WeatherForecastService
import com.testleancould.dodo.flashdemo.net.WeatherService
import com.testleancould.dodo.flashdemo.net.callback.RequestCallback
import com.testleancould.dodo.flashdemo.net.callback.RequestForecastCallBack
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by adamDeng on 2019/10/28
 * Copyright © 2019年 . All rights reserved.
 */
class WeatherRequest {
    private var retrofit = Retrofit.Builder()
        .baseUrl("https://free-api.heweather.net/s6/weather/")
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
    private var service: WeatherService=retrofit.create(WeatherService::class.java)
    private var forecastService:WeatherForecastService=retrofit.create(WeatherForecastService::class.java)

    fun requestWeather(location: String,callback:RequestCallback){
        val call=service.getNowWeather(location,"288367e25c264a1bb63aff12da05e278")
        call.enqueue(object :Callback<WeatherBean>{
            override fun onResponse(call: Call<WeatherBean>, response: Response<WeatherBean>) {
                callback.onResult(response)
            }

            override fun onFailure(call: Call<WeatherBean>, t: Throwable) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        })
    }

    fun requestWeatherForecast(location: String,callback: RequestForecastCallBack){
        val call=forecastService.getWeatherForecast(location,"288367e25c264a1bb63aff12da05e278")
        call.enqueue(object :Callback<WeatherForecast>{
            override fun onResponse(
                call: Call<WeatherForecast>,
                response: Response<WeatherForecast>
            ) {
                callback.onResult(response)
            }

            override fun onFailure(call: Call<WeatherForecast>, t: Throwable) {
                Log.i("数据太多","你去死吧！！！！！！！！！！！！！！！！！！！！！！！！")
            }
        })
    }



}