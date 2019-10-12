package com.testleancould.dodo.flashdemo.data

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.testleancould.dodo.flashdemo.bean.Message
import com.testleancould.dodo.flashdemo.bean.Photo

/**
 * Created by adamDeng on 2019/10/11
 * Copyright © 2019年 深圳市云歌人工智能技术有限公司. All rights reserved.
 */
class PhotoFactory : DataSource.Factory<Int, Photo.ResultBean>() {

    private val mutableLiveData = MutableLiveData<PhotoDataSource>()

    override fun create(): DataSource<Int, Photo.ResultBean> {
        val photoDataSource = PhotoDataSource()
        mutableLiveData.postValue(photoDataSource)
        return photoDataSource
    }
}