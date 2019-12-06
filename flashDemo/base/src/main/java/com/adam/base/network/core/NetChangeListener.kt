package com.adam.base.network.core

/**
 * Created by adamDeng on 2019/11/13
 * Copyright © 2019年 . All rights reserved.
 */
interface NetChangeListener {
    /**
     * 已连接
     * @param netType NetType
     */
    fun onConnect(netType: NetType)

    /**
     * 连接断开
     */
    fun onDisConnect()
}
