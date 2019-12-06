package com.testleancould.dodo.flashdemo.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.testleancould.dodo.flashdemo.bean.Photo
import com.testleancould.dodo.flashdemo.data.PhotoFactory

/**
 * Created by adamDeng on 2019/10/11
 * Copyright © 2019年 深圳市云歌人工智能技术有限公司. All rights reserved.
*/
class PhotoViewModel : ViewModel() {
    val pagedListLiveData: LiveData<PagedList<Photo.ResultBean>>
    private val dataSource: DataSource<Int, Photo.ResultBean>

    init {
        val photoFactory = PhotoFactory()
        dataSource = photoFactory.create()
        pagedListLiveData = LivePagedListBuilder(photoFactory, 10).build()

    }

    fun invalidateDataSource() {
        dataSource.invalidate()
    }
}