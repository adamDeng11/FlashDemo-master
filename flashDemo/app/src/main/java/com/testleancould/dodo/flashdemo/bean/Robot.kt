package com.testleancould.dodo.flashdemo.bean

/**
 * Created by adamDeng on 2019/11/5
 * Copyright © 2019年 . All rights reserved.
 */

/**
 * 返回数据响应体实体类
 */
data class Robot(
    val intent: Intent,
    val results: List<Result>
)

data class Intent(
    val actionName: String,
    val code: Int,
    val intentName: String,
    val parameters: Parameters
)

data class Parameters(
    val nearby_place: String
)

data class Result(
    val groupType: Int,
    val resultType: String,
    val values: Values
)

data class Values(
    val text: String
)