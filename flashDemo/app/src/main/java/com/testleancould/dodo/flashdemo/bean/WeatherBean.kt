package com.testleancould.dodo.flashdemo.bean

/**
 * Created by adamDeng on 2019/10/28
 * Copyright © 2019年 . All rights reserved.
 */
data class WeatherBean(
    val HeWeather6: List<HeWeather6>
)

data class HeWeather6(
    val basic: Basic,
    val now: Now,
    val status: String,
    val update: Update
)

data class Basic(
    val admin_area: String,
    val cid: String,
    val cnty: String,
    val lat: String,
    val location: String,
    val lon: String,
    val parent_city: String,
    val tz: String
)

data class Now(
    val cloud: String,
    val cond_code: String,
    val cond_txt: String,
    val fl: String,
    val hum: String,
    val pcpn: String,
    val pres: String,
    val tmp: String,
    val vis: String,
    val wind_deg: String,
    val wind_dir: String,
    val wind_sc: String,
    val wind_spd: String
)

data class Update(
    val loc: String,
    val utc: String
)