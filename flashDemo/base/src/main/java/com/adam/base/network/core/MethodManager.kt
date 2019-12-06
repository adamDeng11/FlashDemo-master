package com.adam.base.network.core

import java.lang.reflect.Method

/**
 * Created by adamDeng on 2019/11/13
 * Copyright © 2019年 . All rights reserved.
 * 保存符合要求的网络监听注解方法
 */

class MethodManager(//方法的类型(NetType)
    var type: Class<*>?, //网络(注解)类型(netType = NetType.WIFI)
    var netType: NetType?, //需要执行的方法(自动执行)
    var method: Method?
)

