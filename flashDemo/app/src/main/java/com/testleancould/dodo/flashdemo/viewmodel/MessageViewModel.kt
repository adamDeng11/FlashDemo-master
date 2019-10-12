package com.testleancould.dodo.flashdemo.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.testleancould.dodo.flashdemo.bean.Message
import com.testleancould.dodo.flashdemo.data.MessageFactory

/**
 * Created by adamDeng on 2019/10/9
 * Copyright © 2019年 深圳市云歌人工智能技术有限公司. All rights reserved.
 */
class MessageViewModel : ViewModel() {
    val pagedListLiveData: LiveData<PagedList<Message.ResultBean>>
    private val dataSource: DataSource<Int, Message.ResultBean>

    init {
        val messageFactory = MessageFactory()
        dataSource = messageFactory.create()
        pagedListLiveData = LivePagedListBuilder(messageFactory, 20).build()

    }

    fun invalidateDataSource() {
        dataSource.invalidate()
    }
}
