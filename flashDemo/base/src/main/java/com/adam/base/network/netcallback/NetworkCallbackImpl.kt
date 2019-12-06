package com.adam.base.network.netcallback

import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import android.widget.Toast

import androidx.annotation.RequiresApi

import com.adam.base.network.core.MethodManager
import com.adam.base.network.core.NetType
import com.adam.base.network.*

import java.lang.reflect.InvocationTargetException
import java.lang.reflect.Method
import java.util.ArrayList
import java.util.HashMap

/**
 * Created by adamDeng on 2019/11/13
 * Copyright © 2019年 . All rights reserved.
 * NetworkCallback实现
 */
@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
class NetworkCallbackImpl : ConnectivityManager.NetworkCallback() {
    private val netType: NetType
    private val networkMap: MutableMap<Any, List<MethodManager>>

    init {
        //初始化
        netType = NetType.NONE
        networkMap = HashMap()
    }

    override fun onAvailable(network: Network) {
        super.onAvailable(network)
        Log.d(TAG, "onAvailable: 网络已连接")
    }

    override fun onLost(network: Network) {
        super.onLost(network)
        Log.e(TAG, "onLost: 网络已断开")
    }

    override fun onCapabilitiesChanged(network: Network, networkCapabilities: NetworkCapabilities) {
        super.onCapabilitiesChanged(network, networkCapabilities)
        if (networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)) {
            if (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                Log.d(TAG, "onCapabilitiesChanged: 网络类型为wifi")
                post(NetType.WIFI)
            } else if (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                Log.d(TAG, "onCapabilitiesChanged: 蜂窝网络")
                post(NetType.CMWAP)
            } else {
                Log.d(TAG, "onCapabilitiesChanged: 其他网络")
                post(NetType.AUTO)
            }
        }
    }

    private fun post(netType: NetType) {
        val set = networkMap.keys
        for (observer in set) {
            val methodList = networkMap[observer]
            if (methodList != null) {
                for (methodManager in methodList) {
                    //两者参数比较
                    if (methodManager.type!!.isAssignableFrom(netType.javaClass)) {
                        when (methodManager.netType) {
                            NetType.AUTO -> invoke(methodManager, observer, netType)
                            NetType.WIFI -> if (netType === NetType.WIFI || netType === NetType.NONE) {
                                invoke(methodManager, observer, netType)
                            }
                            NetType.CMWAP -> if (netType === NetType.CMWAP || netType === NetType.NONE) {
                                invoke(methodManager, observer, netType)
                            }
                        }
                    }
                }
            }
        }
    }

    private operator fun invoke(methodManager: MethodManager, observer: Any, netType: NetType) {
        try {
            methodManager.method!!.invoke(observer, netType)
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        } catch (e: InvocationTargetException) {
            e.printStackTrace()
        }

    }

    /**
     * 注册
     *
     * @param observer 观察者(Activity/Fragment)
     */
    fun registerObserver(observer: Any) {
        var methodManagers = networkMap[observer]
        if (methodManagers == null) {
            methodManagers = findAnnotationMethods(observer)
            networkMap[observer] = methodManagers
        }
    }

    private fun findAnnotationMethods(observer: Any): List<MethodManager> {
        val methodList = ArrayList<MethodManager>()
        val clazz = observer.javaClass
        val methods = clazz.declaredMethods
        for (method in methods) {
            //获取方法注解
            val network =
                method.getAnnotation(com.adam.base.network.core.Network::class.java) ?: continue
//方法参数校验
            val parameterTypes = method.parameterTypes
            if (parameterTypes.size != 1) {
                throw RuntimeException(method.name + "有且只有一个参数与")
            }
            val name = parameterTypes[0].name
            if (name == NetType::class.java.name) {
                val methodManager = MethodManager(parameterTypes[0], network.netType, method)
                methodList.add(methodManager)
            }
        }
        return methodList
    }

    /**
     * 解除注册
     *
     * @param observer 观察者(Activity/Fragment)
     */
    fun unRegisterObserver(observer: Any) {
        if (!networkMap.isEmpty()) {
            networkMap.remove(observer)
        }
        Log.d(TAG, "unRegisterObserver: " + observer.javaClass.name + "注销成功")
    }

    /**
     * 应用退出时调用
     */
    fun unRegisterAll() {
        if (!networkMap.isEmpty()) {
            networkMap.clear()
        }
    }

    companion object {
        private val TAG = "NetworkCallbackImpl"
    }
}

