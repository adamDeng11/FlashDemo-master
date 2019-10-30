package com.testleancould.dodo.flashdemo.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import com.testleancould.dodo.flashdemo.activity.SearchCityActivity
import android.app.Activity.RESULT_OK
import android.util.Log
import android.widget.TextView
import com.testleancould.dodo.flashdemo.R
import com.testleancould.dodo.flashdemo.bean.Basic
import com.testleancould.dodo.flashdemo.bean.HeWeather6
import com.testleancould.dodo.flashdemo.bean.WeatherBean
import com.testleancould.dodo.flashdemo.ui.fallingview.FallObject
import com.testleancould.dodo.flashdemo.ui.fallingview.FallingView
import com.testleancould.dodo.flashdemo.util.WeatherRequest
import retrofit2.Response


/**
 * Created by adamDeng on 2019/10/9
 * Copyright © 2019年 深圳市云歌人工智能技术有限公司. All rights reserved.
 */

class WeatherFragment : Fragment(){
    private lateinit var searchCityBtn:ImageButton
    private lateinit var returnedData: String
    private lateinit var cityTv:TextView
    private lateinit var data: ArrayList<Basic>
    private lateinit var requestWeather:WeatherRequest
    private lateinit var fallingView: FallingView
    private lateinit var tempTv:TextView
    private lateinit var condTv:TextView
    private lateinit var windDirTv:TextView
    private lateinit var windScTv:TextView
    private lateinit var pcpnTV:TextView
    private lateinit var humTv:TextView
    private lateinit var presTv:TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view=inflater.inflate(R.layout.fragment_weather,container,false)
        searchCityBtn=view.findViewById(R.id.btn_citySearch)
        cityTv=view.findViewById(R.id.txt_city)
        tempTv=view.findViewById(R.id.txt_temp)
        condTv=view.findViewById(R.id.txt_cond)
        windDirTv=view.findViewById(R.id.wind_dir)
        windScTv=view.findViewById(R.id.wind_sc)
        pcpnTV=view.findViewById(R.id.txt_pcpn)
        humTv=view.findViewById(R.id.txt_hum)
        presTv=view.findViewById(R.id.txt_pres)

        //初始化一个雪花样式的fallObject
        val builder = FallObject.Builder(resources.getDrawable(R.drawable.ic_rain))
        val fallObject = builder
            .setSpeed(7, true)
            .setSize(50, 50, true)
            .setWind(5, true, true)
            .build()
        fallingView = view.findViewById(R.id.fallingView) as FallingView
        fallingView.addFallObject(fallObject, 100)//添加50个下落物体对象

        searchCityBtn.setOnClickListener {
            var intent=Intent(activity,SearchCityActivity::class.java)
            startActivityForResult(intent,1)
        }

        return view
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            1 -> if (resultCode === RESULT_OK) {
                returnedData = data!!.getStringExtra("data_return")
                requestWeather= WeatherRequest()
                requestWeather.requestWeather(returnedData,object :WeatherRequest.CallBack{
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
                    }
                })


            }
        }
    }
}
