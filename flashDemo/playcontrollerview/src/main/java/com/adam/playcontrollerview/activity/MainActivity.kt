package com.adam.playcontrollerview.activity

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.BitmapFactory
import android.media.AudioManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import com.adam.playcontrollerview.R
import com.adam.playcontrollerview.YArcMenuView
import com.adam.playcontrollerview.listener.*
import kotlinx.android.synthetic.main.activity_layout.*

/**
 * Created by adamDeng on 2019/11/20
 * Copyright © 2019年 . All rights reserved.
 */

class MainActivity :AppCompatActivity(){

    private lateinit var mArcMenuView:YArcMenuView
    private lateinit var audioManager:AudioManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_layout)

        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){

            var channelId="chat"
            var channelName="聊天消息"
            var importance=NotificationManager.IMPORTANCE_HIGH
            createNotificationChannels(channelId,channelName,importance)

             channelId="subscribe"
             channelName="订阅消息"
             importance=NotificationManager.IMPORTANCE_DEFAULT
            createNotificationChannels(channelId,channelName,importance)


        }

        audioManager= applicationContext.getSystemService(Context.AUDIO_SERVICE) as AudioManager

        /*mArcMenuView = findViewById(R.id.arc_menu)
        val menuItems = ArrayList<Int>()
        menuItems.add(R.drawable.ic_menu_camera)
        menuItems.add(R.drawable.ic_menu_photo)
        menuItems.add(R.drawable.ic_menu_share)
        mArcMenuView.setMenuItems(menuItems)

        mArcMenuView.setClickItemListener {
             fun clickMenuItem(resId: Int) {
                when (resId) {
                    R.drawable.ic_menu_camera -> Toast.makeText(
                        applicationContext,
                        "点击了相机",
                        Toast.LENGTH_SHORT
                    ).show()
                    R.drawable.ic_menu_photo -> Toast.makeText(
                        applicationContext,
                        "点击了相册",
                        Toast.LENGTH_SHORT
                    ).show()
                    R.drawable.ic_menu_share -> Toast.makeText(
                        applicationContext,
                        "点击了分享",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }*/

        btn_1.setOnClickListener { Toast.makeText(this,"是Toast问题1",Toast.LENGTH_LONG).show() }
        btn_2.setOnClickListener { Toast.makeText(this,"是Toast问题2",Toast.LENGTH_LONG).show() }

        btn_3.setOnClickListener {
            var notificationManager=getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            var notification=NotificationCompat.Builder(this,"chat")
                .setContentTitle("Adam")
                .setContentText("原来是通知")
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.drawable.ic_keep)
                .setLargeIcon(BitmapFactory.decodeResource(resources,R.drawable.ic_keep))
                .setAutoCancel(true)
                .build()
            notificationManager.notify(1,notification)

        }

        btn_4.setOnClickListener {
            var notificationManager=getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            var notification=NotificationCompat.Builder(this,"subscribe")
                .setContentTitle("Adam")
                .setContentText("原来是订阅通知")
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.drawable.ic_keep)
                .setLargeIcon(BitmapFactory.decodeResource(resources,R.drawable.ic_keep))
                .setAutoCancel(true)
                .build()
            notificationManager.notify(2,notification)
        }


        btn_test.setOnDownActionListener(object : OnDownActionListener {
            override fun onDown(x: Int, y: Int) {
               Toast.makeText(this@MainActivity,"你好扇形",Toast.LENGTH_LONG).show()
            }
        })

        btn_test.setOnBackDownActionListener(object : OnBackDownActionListener {
            override fun onDown(x: Int, y: Int) {
                Toast.makeText(this@MainActivity,"你好,后退",Toast.LENGTH_LONG).show()
            }
        })
        btn_test.setOnPauseDownActionListener(object : OnPauseDownActionListener {
            override fun onDown(x: Int, y: Int) {
                Toast.makeText(this@MainActivity,"你好,暂停",Toast.LENGTH_LONG).show()
            }
        })
        btn_test.setOnForwardDownActionListener(object : OnForwardDownActionListener {
            override fun onDown(x: Int, y: Int) {
                Toast.makeText(this@MainActivity,"你好,快进",Toast.LENGTH_LONG).show()
            }
        })

        btn_test.setOnVolumeListener(object : OnVolumeListener {

            override fun onVolumeChange(process: Int) {
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,process/7,0)
            }
        })
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannels(channelId:String, channelName:String, importance:Int){
        var  notificationChannel=NotificationChannel(channelId,channelName,importance)
        var notificationManager=getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(notificationChannel)
    }
}