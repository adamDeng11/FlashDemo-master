package com.testleancould.dodo.flashdemo.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.OrientationHelper
import androidx.recyclerview.widget.RecyclerView
import com.testleancould.dodo.flashdemo.R
import com.testleancould.dodo.flashdemo.activity.NewsItemActivity
import com.testleancould.dodo.flashdemo.adapter.MessageAdapter
import com.testleancould.dodo.flashdemo.ui.decoration.ItemDecoration
import com.testleancould.dodo.flashdemo.viewmodel.MessageViewModel


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

        val view=inflater.inflate(R.layout.fragment_news, container, false)
        recyclerView=view.findViewById(R.id.news_recycleView)
        messageAdapter= MessageAdapter()
        messageViewModel = ViewModelProviders.of(this).get(MessageViewModel::class.java)
        messageViewModel.pagedListLiveData.observe(this, Observer { messageAdapter.submitList(it) })
        messageAdapter.setOnItemClickListener(object : MessageAdapter.OnItemClick{
           override fun onItemClick(position: Int) {
              /* var fm=activity?.supportFragmentManager
               var fragment=NewsItemFragment()
               fm?.beginTransaction()?.replace(R.id.container,fragment)?.commit()*/
               var intent=Intent()
               intent.setClass(activity, NewsItemActivity().javaClass)
               var bundle=Bundle()
               bundle.putString("position",position.toString())
               intent.putExtra("data",bundle)
               startActivity(intent)
            }
        })
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
