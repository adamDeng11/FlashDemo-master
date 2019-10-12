package com.testleancould.dodo.flashdemo.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.testleancould.dodo.flashdemo.GlideApp
import com.testleancould.dodo.flashdemo.adapter.MessageAdapter
import com.testleancould.dodo.flashdemo.net.NewsService
import com.testleancould.dodo.flashdemo.viewmodel.MessageViewModel
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import androidx.recyclerview.widget.OrientationHelper

import com.testleancould.dodo.flashdemo.ui.decoration.ItemDecoration


/**
 * Created by adamDeng on 2019/10/9
 * Copyright © 2019年 深圳市云歌人工智能技术有限公司. All rights reserved.
 */
class NewsFragment : Fragment(){
    private lateinit var messageViewModel: MessageViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var messageAdapter: MessageAdapter
    private lateinit var testBtn: Button
    private lateinit var testImagview: ImageView



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view=inflater.inflate(com.testleancould.dodo.flashdemo.R.layout.fragment_news, container, false)

       /* testBtn=view.findViewById(R.id.btn_test)
        testImagview=view.findViewById(R.id.image_test) as ImageView

        testBtn.setOnClickListener {
            val url = "http://wimg.spriteapp.cn/profile/large/2018/08/14/5b721ea4242da_mini.jpg"
            Log.i("adam",url)
            GlideApp.with(this).load(url).error(R.mipmap.head_show).placeholder(R.mipmap.my_ico).into(testImagview) }*/




        recyclerView=view.findViewById(com.testleancould.dodo.flashdemo.R.id.news_recycleView)
        messageAdapter= MessageAdapter()
        messageViewModel = ViewModelProviders.of(this).get(MessageViewModel::class.java)
        messageViewModel.pagedListLiveData.observe(this, Observer { messageAdapter.submitList(it) })

        recyclerView.adapter = messageAdapter

        val linearLayoutManager = LinearLayoutManager(context)
        linearLayoutManager.orientation = RecyclerView.VERTICAL
        recyclerView.layoutManager=linearLayoutManager

        context?.let { ItemDecoration(it, OrientationHelper.VERTICAL) }?.let {
            recyclerView.addItemDecoration(
                it
            )
        }



        return view
    }
}
