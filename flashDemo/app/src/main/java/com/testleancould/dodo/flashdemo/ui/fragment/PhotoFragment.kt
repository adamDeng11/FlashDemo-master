package com.testleancould.dodo.flashdemo.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.testleancould.dodo.flashdemo.R
import com.testleancould.dodo.flashdemo.adapter.PhotoAdapter
import com.testleancould.dodo.flashdemo.viewmodel.PhotoViewModel

/**
 * Created by adamDeng on 2019/10/9
 * Copyright © 2019年 深圳市云歌人工智能技术有限公司. All rights reserved.
 */
class PhotoFragment : Fragment() {
    private lateinit var photoViewModel: PhotoViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var photoAdapter: PhotoAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        saveInstanceState: Bundle?
    ): View? {

       val view=inflater.inflate(R.layout.fragment_photo, container, false)

        recyclerView=view.findViewById(R.id.photo_recycleView)
        photoAdapter= PhotoAdapter()
        photoViewModel=ViewModelProviders.of(this).get(PhotoViewModel::class.java)
        photoViewModel.pagedListLiveData.observe(this, Observer { photoAdapter.submitList(it) })
        recyclerView.adapter=photoAdapter

        val linearLayoutManager = StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL)
        recyclerView.layoutManager=linearLayoutManager

        return view


    }
}
