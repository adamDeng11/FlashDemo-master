package com.adam.base.network.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

import com.adam.base.network.Constants
import com.adam.base.network.NetworkListener
import com.adam.base.network.core.MethodManager
import com.adam.base.network.core.NetType
import com.adam.base.network.core.Network
import com.adam.base.network.utils.NetworkUtils

import java.lang.reflect.InvocationTargetException
import java.lang.reflect.Method
import java.util.ArrayList
import java.util.HashMap

/**
 * Created by adamDeng on 2019/11/13
 * Copyright © 2019年 . All rights reserved.
 */
class NetworkStateReceiverWithAnno : BroadcastReceiver() {
    private var netType: NetType? = null
    private val networkMap: MutableMap<Any, List<MethodManager>>

    init {
        //初始化网络连接状态
        netType = NetType.NONE
        networkMap = HashMap()
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
            } else {
                Log.e(TAG, "onReceive: 网络连接失败")
            }
            post(netType)
        }
    }

    /**
     * 分发
     *
     * @param netType NetType
     */
    private fun post(netType: NetType?) {
        //Activity集合
        val set = networkMap.keys
        for (observer in set) {
            //所有注解的方法
            val methodList = networkMap[observer]
            if (methodList != null) {
                for (methodManager in methodList) {
                    //两者参数比较
                    if (methodManager.type!!.isAssignableFrom(netType!!.javaClass)) {
                        when (methodManager.netType) {
                            NetType.AUTO -> invoke(methodManager, observer, netType)
                            NetType.WIFI -> if (netType === NetType.WIFI || netType === NetType.NONE) {
                                invoke(methodManager, observer, netType)
                            }
                            NetType.CMWAP -> if (netType === NetType.CMWAP || netType === NetType.NONE) {
                                invoke(methodManager, observer, netType)
                            }
                            NetType.CMNET -> if (netType === NetType.CMNET || netType === NetType.NONE) {
                                invoke(methodManager, observer, netType)
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * 在Activity中执行方法，参数为NetType
     *
     * @param methodManager
     * @param observer
     * @param netType
     */
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
     * 获取Activity/Fragment中的注解方法并添加到networkList中
     *
     * @param observer 观察者(Activity/Fragment)
     */
    fun registerObserver(observer: Any) {
        var methodList = networkMap[observer]
        if (methodList == null) {
            methodList = findAnnotationMethod(observer)
            networkMap[observer] = methodList
        }
    }

    /**
     * 获取Activity/Fragment中的注解方法
     *
     * @param observer 观察者(Activity/Fragment)
     * @return
     */
    private fun findAnnotationMethod(observer: Any): List<MethodManager> {
        val methodList = ArrayList<MethodManager>()
        val clazz = observer.javaClass
        //获取Activity中的所有公有方法，包括其父类及其实现的接口的方法，因方法数过多，此处使用getDeclaredMethods()
        //Method[] methods = clazz.getMethods();
        //获取Activity中的所有方法，包括公共、保护、默认(包)访问和私有方法,当然也包括它所实现接口的方法，但不包括继承的方法
        val methods = clazz.declaredMethods
        for (method in methods) {
            //获取方法的注解
            val network = method.getAnnotation(Network::class.java) ?: continue
//方法参数校验
            val parameterTypes = method.parameterTypes
            if (parameterTypes.size != 1) {
                throw RuntimeException(method.name + "方法有且只有一个参数")
            }
            //参数类型校验
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
        Log.d(TAG, "unRegisterObserver:" + observer.javaClass.name + "注销成功")
    }

    /**
     * 应用退出时调用
     */
    fun unRegisterAll() {
        if (!networkMap.isEmpty()) {
            networkMap.clear()
        }
        NetworkListener.instance?.context?.unregisterReceiver(this)
    }

    companion object {
        private val TAG = "NetStateWithAnno"
    }
}

