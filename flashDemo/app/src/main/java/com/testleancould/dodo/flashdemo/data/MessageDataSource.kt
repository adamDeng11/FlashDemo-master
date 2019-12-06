package com.testleancould.dodo.flashdemo.data

import androidx.paging.PositionalDataSource
import com.testleancould.dodo.flashdemo.bean.Message
import com.testleancould.dodo.flashdemo.net.NewsService
import io.reactivex.disposables.Disposable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by adamDeng on 2019/10/9
 * Copyright © 2019年 深圳市云歌人工智能技术有限公司. All rights reserved.
 */
class MessageDataSource : PositionalDataSource<Message.ResultBean>() {


    private val retrofit = Retrofit.Builder()//创建Retrofit实例
        .baseUrl("https://api.apiopen.top/") //这里需要传入url的域名部分
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())//返回的数据经过转换工厂转换成我们想要的数据，最常用的就是Gson
        .build()
    private val mService: NewsService

    init {
        mService = retrofit.create(NewsService::class.java!!)
    }

    override fun loadInitial(params: LoadInitialParams, callback: LoadInitialCallback<Message.ResultBean>) {

        fetchItem(0, params.pageSize, object : Callback {
            override fun onResult(value: Message) {
                callback.onResult(value.result as MutableList<Message.ResultBean>, 0, 1000)

            }
        })

    }

    override fun loadRange(params: LoadRangeParams, callback: LoadRangeCallback<Message.ResultBean>) {

        fetchItem(params.startPosition, params.loadSize, object : Callback {
            override fun onResult(value: Message) {

                callback.onResult(value.result as MutableList<Message.ResultBean>)


            }
        })

    }

    private fun fetchItem(startPosition: Int, size: Int, callback: Callback) {
        mService.getMessage(startPosition, size).subscribe(object : io.reactivex.Observer<Message> {
            override fun onSubscribe(d: Disposable) {

            }

            override fun onNext(value: Message) {
                callback.onResult(value)

            }

            override fun onError(e: Throwable) {

            }

            override fun onComplete() {

            }
        })
    }



    private interface Callback {

        fun onResult(value: Message)
    }

}
