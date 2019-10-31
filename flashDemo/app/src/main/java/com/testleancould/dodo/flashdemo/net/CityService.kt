package com.testleancould.dodo.flashdemo.net

import com.testleancould.dodo.flashdemo.bean.CityBean
import io.reactivex.Observable
import retrofit2.http.*

/**
 * Created by adamDeng on 2019/10/28
 * Copyright © 2019年 . All rights reserved.
 */
interface CityService {
    @GET("find")
    fun getCity(@Query("location") location: String, @Query("key") key: String, @Query("group") group:String): retrofit2.Call<CityBean>
}