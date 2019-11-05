package com.testleancould.dodo.flashdemo.bean

/**
 * Created by adamDeng on 2019/11/1
 * Copyright © 2019年 . All rights reserved.
 */
data class WeatherForecast(
    val HeWeather6: List<HeWeather6Forecast>
)

data class HeWeather6Forecast(
    val basic: BasicForecast,
    val daily_forecast: List<DailyForecast>,
    val status: String,
    val update: UpdateForecast
)

data class BasicForecast(
    val admin_area: String,
    val cid: String,
    val cnty: String,
    val lat: String,
    val location: String,
    val lon: String,
    val parent_city: String,
    val tz: String
)

data class DailyForecast(
    val cond_code_d: String,
    val cond_code_n: String,
    val cond_txt_d: String,
    val cond_txt_n: String,
    val date: String,
    val hum: String,
    val mr: String,
    val ms: String,
    val pcpn: String,
    val pop: String,
    val pres: String,
    val sr: String,
    val ss: String,
    val tmp_max: String,
    val tmp_min: String,
    val uv_index: String,
    val vis: String,
    val wind_deg: String,
    val wind_dir: String,
    val wind_sc: String,
    val wind_spd: String
)

data class UpdateForecast(
    val loc: String,
    val utc: String
)