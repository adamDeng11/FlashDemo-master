package com.adam.base.network.core

import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy

/**
 * Created by adamDeng on 2019/11/13
 * Copyright © 2019年 . All rights reserved.
 */
@Target(
    AnnotationTarget.FUNCTION,
    AnnotationTarget.PROPERTY_GETTER,
    AnnotationTarget.PROPERTY_SETTER
)//作用在方法之上
@Retention(RetentionPolicy.RUNTIME)//jvm运行时，通过反射获取该注解的值
annotation class Network(val netType: NetType)
