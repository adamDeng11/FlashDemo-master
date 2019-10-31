package com.testleancould.dodo.flashdemo.bean

/**
 * Created by adamDeng on 2019/10/28
 * Copyright © 2019年 . All rights reserved.
 */
data class CityBean(
    val HeWeather6: List<HeWeather6City>
)

data class HeWeather6City(
    val basic: List<BasicCity>,
    val status: String
)

data class BasicCity(
    val admin_area: String,
    val cid: String,
    val cnty: String,
    val lat: String,
    val location: String,
    val lon: String,
    val parent_city: String,
    val type: String,
    val tz: String
)