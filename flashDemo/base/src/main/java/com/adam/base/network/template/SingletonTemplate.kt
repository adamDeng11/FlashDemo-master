package com.adam.base.network.template

/**
 * Created by adamDeng on 2019/11/13
 * Copyright © 2019年 . All rights reserved.
 */
abstract class SingletonTemplate<T> {

    private var mInstance: T? = null

    protected abstract fun create(): T

    fun get(): T? {
        synchronized(this) {
            if (mInstance == null) {
                mInstance = create()
            }
            return mInstance
        }
    }
}
