package com.testleancould.dodo.flashdemo.ui.fragment

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.adam.base.weatherview.SunAnimationView
import com.adam.base.weatherview.WeatherChartView
import com.testleancould.dodo.flashdemo.R
import com.testleancould.dodo.flashdemo.activity.SearchCityActivity
import com.testleancould.dodo.flashdemo.bean.WeatherBean
import com.testleancould.dodo.flashdemo.bean.WeatherForecast
import com.testleancould.dodo.flashdemo.net.callback.RequestCallback
import com.testleancould.dodo.flashdemo.net.callback.RequestForecastCallBack
import com.testleancould.dodo.flashdemo.ui.fallingview.FallObject
import com.testleancould.dodo.flashdemo.ui.fallingview.FallingView
import com.testleancould.dodo.flashdemo.util.WeatherRequest
import kotlinx.android.synthetic.main.fragment_weather.*
import kotlinx.android.synthetic.main.weather_7days.*
import kotlinx.android.synthetic.main.weather_7status.*
import kotlinx.android.synthetic.main.weather_details.*
import retrofit2.Response
import java.util.*

/**
 * Created by adamDeng on 2019/10/9
 * Copyright © 2019年 深圳市云歌人工智能技术有限公司. All rights reserved.
 */

class WeatherFragment : Fragment(){
    private lateinit var objectAnimator: ObjectAnimator
    private lateinit var searchCityBtn:ImageButton
    private lateinit var refreshCityBtn:ImageButton
    private lateinit var returnedData: String
    private lateinit var cityTv:TextView
    private lateinit var requestWeather:WeatherRequest

    private lateinit var fallingView: FallingView
    private lateinit var weatherLine: WeatherChartView

    private lateinit var tempTv:TextView
    private lateinit var condTv:TextView
    private lateinit var windDirTv:TextView
    private lateinit var windScTv:TextView
    private lateinit var pcpnTV:TextView
    private lateinit var humTv:TextView
    private lateinit var presTv:TextView
    private lateinit var sunAnimationView: SunAnimationView
    private lateinit var moonAnimationView: SunAnimationView



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view=inflater.inflate(R.layout.fragment_weather,container,false)
        refreshCityBtn=view.findViewById(R.id.btn_cityRefresh)
        searchCityBtn=view.findViewById(R.id.btn_citySearch)
        cityTv=view.findViewById(R.id.txt_city)
        tempTv=view.findViewById(R.id.txt_temp)
        condTv=view.findViewById(R.id.txt_cond)
        windDirTv=view.findViewById(R.id.wind_dir)
        windScTv=view.findViewById(R.id.wind_sc)
        pcpnTV=view.findViewById(R.id.txt_pcpn)
        humTv=view.findViewById(R.id.txt_hum)
        presTv=view.findViewById(R.id.txt_pres)
        fallingView = view.findViewById(R.id.fallingView) as FallingView
        weatherLine = view.findViewById(R.id.weather_line)
        sunAnimationView=view.findViewById(R.id.sunView)
        moonAnimationView=view.findViewById(R.id.moonView)

        init()

        return view
    }

    fun init(){
        //初始化一个雪花样式的fallObject
        val builder = FallObject.Builder(resources.getDrawable(R.drawable.ic_rain))
        val fallObject = builder
            .setSpeed(7, true)
            .setSize(50, 50, true)
            .setWind(5, true, true)
            .build()
        fallingView.addFallObject(fallObject, 100)//添加50个下落物体对象

        // 设置白天温度曲线
        weatherLine.setTempDay(intArrayOf(4, 23, 11, 22, 34, 35))
        // 设置夜间温度曲线
        weatherLine.setTempNight(intArrayOf(3, 2, 7, 21, 34, 12))
        weatherLine.invalidate()

        //绘制半圆
        sunAnimationView.setTimes("06:00","17:00","16:00")
        sunAnimationView.setIcon(R.drawable.icon_sun)
        moonAnimationView.setTimes("07:00","17:00","08:00")
        moonAnimationView.setIcon(R.drawable.icon_moon)


        searchCityBtn.setOnClickListener {
            var intent=Intent(activity,SearchCityActivity::class.java)
            startActivityForResult(intent,1)
        }
        refreshCityBtn.setOnClickListener{
            /*refreshCityBtn.animate().rotation(360f).start()*/
            objectAnimator= ObjectAnimator.ofFloat(btn_cityRefresh,"rotation",360f)
            objectAnimator.duration=1000
            objectAnimator.start()
        }


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            1 -> if (resultCode === RESULT_OK) {
                returnedData = data!!.getStringExtra("data_return")
                requestWeather= WeatherRequest()
                requestWeather.requestWeather(returnedData,object :RequestCallback{
                    override fun onResult(weatherBean: Response<WeatherBean>) {
                        var now=weatherBean.body()!!.HeWeather6[0].now
                        cityTv.text=weatherBean.body()!!.HeWeather6[0].basic.location
                        tempTv.text=now.tmp+"℃"
                        condTv.text=now.cond_txt
                        windDirTv.text=now.wind_dir
                        windScTv.text=now.wind_sc+"级"
                        pcpnTV.text=now.pcpn+"mm"
                        humTv.text=now.hum+"%"
                        presTv.text=now.pres+"hPa"
                        weather_temp.text=now.tmp+"℃"
                        weather_pcpn.text=now.pcpn+"mm"
                        weather_hum.text=now.hum+"%"
                        weather_pre.text=now.pres+"hPa"
                        weather_vis.text=now.vis+"km"
                    }
                })
                requestWeather.requestWeatherForecast(returnedData,object :RequestForecastCallBack{
                    @SuppressLint("SimpleDateFormat")
                    override fun onResult(weatherForecastBean: Response<WeatherForecast>) {
                        var forecast=weatherForecastBean.body()!!.HeWeather6[0].daily_forecast

                        //七天日期
                        var day1=forecast[0].date
                        var day2=forecast[1].date
                        var day3=forecast[2].date
                        var day4=forecast[3].date
                        var day5=forecast[4].date
                        var day6=forecast[5].date
                        tv_day1.text=day1.substring(day1.length-2,day1.length)+"日"
                        tv_day2.text=day2.substring(day2.length-2,day2.length)+"日"
                        tv_day3.text=day3.substring(day3.length-2,day3.length)+"日"
                        tv_day4.text=day4.substring(day4.length-2,day4.length)+"日"
                        tv_day5.text=day5.substring(day5.length-2,day5.length)+"日"
                        tv_day6.text=day6.substring(day6.length-2,day6.length)+"日"

                        //七天天气状况
                        tv_day1_status.text=forecast[0].cond_txt_d
                        tv_day2_status.text=forecast[1].cond_txt_d
                        tv_day3_status.text=forecast[2].cond_txt_d
                        tv_day4_status.text=forecast[3].cond_txt_d
                        tv_day5_status.text=forecast[4].cond_txt_d
                        tv_day6_status.text=forecast[5].cond_txt_d


                        var dayMax1=forecast[0].tmp_max.toInt()
                        var dayMax2=forecast[1].tmp_max.toInt()
                        var dayMax3=forecast[2].tmp_max.toInt()
                        var dayMax4=forecast[3].tmp_max.toInt()
                        var dayMax5=forecast[4].tmp_max.toInt()
                        var dayMax6=forecast[5].tmp_max.toInt()
                        var dayMin1=forecast[0].tmp_min.toInt()
                        var dayMin2=forecast[1].tmp_min.toInt()
                        var dayMin3=forecast[2].tmp_min.toInt()
                        var dayMin4=forecast[3].tmp_min.toInt()
                        var dayMin5=forecast[4].tmp_min.toInt()
                        var dayMin6=forecast[5].tmp_min.toInt()

                        // 设置白天温度曲线
                        weatherLine.setTempDay(intArrayOf(dayMax1,dayMax2,dayMax3,dayMax4,dayMax5,dayMax6))
                        //设置夜晚温度曲线
                        weatherLine.setTempNight(intArrayOf(dayMin1,dayMin2,dayMin3,dayMin4,dayMin5,dayMin6))

                        //获取系统当前时间
                        var calendar=Calendar.getInstance()
                        var hour=calendar.get(Calendar.HOUR_OF_DAY).toString()
                        var minute=calendar.get(Calendar.MINUTE).toString()
                        var currentTime= "$hour:$minute"
                        Log.i("当前时间:",currentTime)

                        //日出日落
                        var sr=forecast[0].sr
                        var ss=forecast[0].ss
                        sunAnimationView.setTimes(sr,ss,currentTime)

                        //月升月落
                        var mr=forecast[0].mr
                        var ms=forecast[0].ms
                        moonAnimationView.setTimes(mr,ms,currentTime)

                    }
                })
            }
        }
    }
}
