package com.testleancould.dodo.flashdemo.data

import androidx.paging.PositionalDataSource
import com.testleancould.dodo.flashdemo.bean.Photo
import com.testleancould.dodo.flashdemo.net.PhotoService
import io.reactivex.disposables.Disposable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by adamDeng on 2019/10/11
 * Copyright © 2019年 深圳市云歌人工智能技术有限公司. All rights reserved.
 */

class PhotoDataSource : PositionalDataSource<Photo.ResultBean>() {


    private val retrofit = Retrofit.Builder()//创建Retrofit实例
        .baseUrl("https://api.apiopen.top/") //这里需要传入url的域名部分
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())//返回的数据经过转换工厂转换成我们想要的数据，最常用的就是Gson
        .build()
    private val mService:PhotoService

    init {
        mService = retrofit.create(PhotoService::class.java!!)
    }

    override fun loadInitial(params: LoadInitialParams, callback: LoadInitialCallback<Photo.ResultBean>) {

        fetchItem(0, params.pageSize, object : Callback {
            override fun onResult(value: Photo) {
                callback.onResult(value.result as MutableList<Photo.ResultBean>, 0, 1000)

            }
        })

    }

    override fun loadRange(params: LoadRangeParams, callback: LoadRangeCallback<Photo.ResultBean>) {

        fetchItem(0, params.loadSize, object : Callback {
            override fun onResult(value: Photo) {

                callback.onResult(value.result as MutableList<Photo.ResultBean>)

            }
        })


    }

    private fun fetchItem(startPosition: Int, size: Int, callback: Callback) {
        mService.getPhoto(startPosition, size).subscribe(object : io.reactivex.Observer<Photo> {
            override fun onSubscribe(d: Disposable) {

            }

            override fun onNext(value: Photo) {
                callback.onResult(value)

            }

            override fun onError(e: Throwable) {

            }

            override fun onComplete() {

            }
        })
    }



    private interface Callback {

        fun onResult(value: Photo)
    }

}