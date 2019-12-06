package com.testleancould.dodo.flashdemo.net

import com.testleancould.dodo.flashdemo.bean.Photo
import io.reactivex.Observable
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

/**
 * Created by adamDeng on 2019/10/11
 * Copyright © 2019年 深圳市云歌人工智能技术有限公司. All rights reserved.
 */
interface PhotoService {

    @POST("getImages" )
    @FormUrlEncoded
    fun getPhoto(@Field("page") page: Int, @Field("count") count: Int): Observable<Photo>
}