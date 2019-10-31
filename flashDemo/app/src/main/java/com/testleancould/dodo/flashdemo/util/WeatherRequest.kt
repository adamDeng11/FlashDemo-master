package com.testleancould.dodo.flashdemo.util

import com.testleancould.dodo.flashdemo.bean.Basic
import com.testleancould.dodo.flashdemo.bean.WeatherBean
import com.testleancould.dodo.flashdemo.net.WeatherService
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
    private lateinit var weatherData:ArrayList<Basic>

    fun requestWeather(location: String,callback:CallBack){
        val call=service!!.getNowWeather(location,"288367e25c264a1bb63aff12da05e278")
        call.enqueue(object :Callback<WeatherBean>{
            override fun onResponse(call: Call<WeatherBean>, response: Response<WeatherBean>) {
                /*val bean=response.body()
                val now=bean!!.HeWeather6[0].now.cond_txt*/
                /*weatherData.addAll(listOf(response.body()!!.HeWeather6[0].basic))*/
               /* Log.i("天气11111111111111111111",now)*/
                callback.onResult(response)


            }

            override fun onFailure(call: Call<WeatherBean>, t: Throwable) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        })
    }

    interface CallBack{
        fun onResult(weatherBean: Response<WeatherBean>)
    }

}