package com.testleancould.dodo.flashdemo.net

import android.telecom.Call
import com.testleancould.dodo.flashdemo.bean.Message
import io.reactivex.Observable
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import java.util.*

/**
 * Created by adamDeng on 2019/10/9
 * Copyright © 2019年 深圳市云歌人工智能技术有限公司. All rights reserved.
 */
interface NewsService {

    @POST("getWangYiNews")
    @FormUrlEncoded
    fun getMessage(@Field("page") page: Int, @Field("count") count: Int): Observable<Message>


}