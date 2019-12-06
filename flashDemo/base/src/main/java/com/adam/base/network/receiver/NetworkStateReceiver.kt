package com.adam.base.network.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

import com.adam.base.network.Constants
import com.adam.base.network.core.NetChangeListener
import com.adam.base.network.core.NetType
import com.adam.base.network.utils.NetworkUtils

/**
 * Created by adamDeng on 2019/11/13
 * Copyright © 2019年 . All rights reserved.
 * 广播接收，使用接口回调形式
 */
class NetworkStateReceiver : BroadcastReceiver() {
    private var netType: NetType? = null
    private var listener: NetChangeListener? = null

    init {
        //初始化网络连接状态
        netType = NetType.NONE
    }

    fun setListener(listener: NetChangeListener) {
        this.listener = listener
    }


    override fun onReceive(context: Context, intent: Intent?) {
        if (intent == null || intent.action == null) {
            Log.e(TAG, "onReceive: 异常")
            return
        }
        if (intent.action == Constants.ANDROID_NET_CHANGE_ACTION) {
            Log.d(TAG, "onReceive: 网络发生变化")
            netType = NetworkUtils.getNetType()
            if (NetworkUtils.isNetworkAvailable()) {
                Log.d(TAG, "onReceive: 网络连接成功")
                if (listener != null) {
                    listener!!.onConnect(netType!!)
                }
            } else {
                Log.e(TAG, "onReceive: 网络连接失败")
                if (listener != null) {
                    listener!!.onDisConnect()
                }
            }
        }
    }

    companion object {
        private val TAG = "NetworkStateReceiver"
    }
}

