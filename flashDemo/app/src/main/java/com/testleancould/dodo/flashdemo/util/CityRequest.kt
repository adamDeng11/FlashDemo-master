package com.testleancould.dodo.flashdemo.util

import com.testleancould.dodo.flashdemo.bean.BasicCity
import com.testleancould.dodo.flashdemo.net.CityService
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by adamDeng on 2019/10/28
 * Copyright © 2019年 . All rights reserved.
 */
class CityRequest {
    private var retrofit = Retrofit.Builder()  //创建Retrofit实例
        .baseUrl("https://search.heweather.net/")    //这里需要传入url的域名部分
        .addConverterFactory(GsonConverterFactory.create()) //返回的数据经过转换工厂转换成我们想要的数据，最常用的就是Gson
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()   //构建实例
    private var service: CityService=retrofit.create(CityService::class.java)
    lateinit var data:ArrayList<BasicCity>

    /*fun requestCity(location: String){
        val call = service.getCity(location,"288367e25c264a1bb63aff12da05e278","cn")
        call.subscribe(object : io.reactivex.Observer<CityBean> {
            override fun onNext(value: CityBean?) {
                *//*val bean=value
                val basic=bean!!.HeWeather6[0].basic
                if (basic!=null){
                    data.addAll(bean.HeWeather6[0].basic)

                }*//*

            }

            override fun onSubscribe(d: Disposable) {

            }

            override fun onError(e: Throwable) {


            }

            override fun onComplete() {

            }
        })

    }*/
}