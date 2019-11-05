package com.testleancould.dodo.flashdemo.net

import com.testleancould.dodo.flashdemo.bean.Ask
import com.testleancould.dodo.flashdemo.bean.Robot
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.POST

/**
 * Created by adamDeng on 2019/11/5
 * Copyright © 2019年 . All rights reserved.
 */
interface RobotService {
    @POST("openapi/api/v2")
    fun getRobot(@Body ask: Ask):Call<Robot>
}