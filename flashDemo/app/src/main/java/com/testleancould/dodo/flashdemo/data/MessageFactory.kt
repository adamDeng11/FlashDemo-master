package com.testleancould.dodo.flashdemo.data

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.testleancould.dodo.flashdemo.bean.Message

/**
 * Created by adamDeng on 2019/10/9
 * Copyright © 2019年 深圳市云歌人工智能技术有限公司. All rights reserved.
 */
class MessageFactory : DataSource.Factory<Int, Message.ResultBean>() {

    private val mutableLiveData = MutableLiveData<MessageDataSource>()

    override fun create(): DataSource<Int, Message.ResultBean> {
        val messageDataSource = MessageDataSource()
        mutableLiveData.postValue(messageDataSource)
        return messageDataSource
    }
}