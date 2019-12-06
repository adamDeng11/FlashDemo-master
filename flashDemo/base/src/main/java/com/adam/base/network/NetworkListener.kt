package com.adam.base.network

import android.annotation.SuppressLint
import android.content.Context
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.NetworkRequest
import android.os.Build

import com.adam.base.network.netcallback.NetworkCallbackImpl
import com.adam.base.network.receiver.NetworkStateReceiverWithAnno
import com.adam.base.network.template.SingletonTemplate

/**
 * Created by adamDeng on 2019/11/13
 * Copyright © 2019年 . All rights reserved.
 */
class NetworkListener
/**
 * 私有化构造方法
 */
private constructor() {
    var context: Context? = null
        private set
    private var networkCallback: NetworkCallbackImpl? = null
    private var receiver: NetworkStateReceiverWithAnno? = null

    /**
     * 版本是否是5.0及以上
     *
     * @return true/false
     */
    private val isHigherThenLollipop: Boolean
        get() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP

    /**
     * 初始化
     *
     * @param context context
     */
    @SuppressLint("MissingPermission")
    fun init(context: Context) {
        this.context = context
        if (isHigherThenLollipop) {
            networkCallback = NetworkCallbackImpl()
            val builder = NetworkRequest.Builder()
            val request = builder.build()
            val connMgr =
                NetworkListener.instance!!.context!!.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            connMgr?.registerNetworkCallback(request, networkCallback)
        } else {
            //5.0以下继续使用广播
            receiver = NetworkStateReceiverWithAnno()
            val filter = IntentFilter()
            filter.addAction(Constants.ANDROID_NET_CHANGE_ACTION)
        }
    }

    /**
     * 注册
     *
     * @param observer 观察者(Activity/Fragment)
     */
    fun registerObserver(observer: Any) {
        if (isHigherThenLollipop) {
            networkCallback!!.registerObserver(observer)
        } else {
            receiver!!.registerObserver(observer)
        }
    }

    /**
     * 解除注册
     *
     * @param observer 观察者(Activity/Fragment)
     */
    fun unRegisterObserver(observer: Any) {
        if (isHigherThenLollipop) {
            networkCallback!!.unRegisterObserver(observer)
        } else {
            receiver!!.unRegisterObserver(observer)
        }
    }

    companion object {

        private val INSTANCE = object : SingletonTemplate<NetworkListener>() {
            override fun create(): NetworkListener {
                return NetworkListener()
            }
        }

        val instance: NetworkListener?
            get() = INSTANCE.get()
    }
}

