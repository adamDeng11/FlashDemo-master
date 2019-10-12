package com.testleancould.dodo.flashdemo.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.testleancould.dodo.flashdemo.GlideApp
import com.testleancould.dodo.flashdemo.bean.Message
import com.testleancould.dodo.flashdemo.bean.Photo
import android.app.Activity
import android.R
import com.bumptech.glide.Glide
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import androidx.annotation.NonNull





/**
 * Created by adamDeng on 2019/10/11
 * Copyright © 2019年 深圳市云歌人工智能技术有限公司. All rights reserved.
 */

class PhotoAdapter : PagedListAdapter<Photo.ResultBean, PhotoAdapter.VH>(resultBeanItemCallback) {
    private var context: Context? = null
    //② 创建ViewHolder
    class VH(itemView: View) : RecyclerView.ViewHolder(itemView) {

        internal var photo: ImageView

        init {

            photo = itemView.findViewById(com.testleancould.dodo.flashdemo.R.id.photo_show)
            val width = (photo.context as Activity).windowManager.defaultDisplay.width
            val params = photo.layoutParams

            params.width = width/2
            params.height = (200 + Math.random() * 400).toInt()
            photo.layoutParams=params



        }

    }

    //③ 在Adapter中实现3个方法
    override fun onBindViewHolder(holder: VH, position: Int) {


        val resultBean = getItem(position)

        context?.let {
            GlideApp.with(it).load(resultBean!!.img)
                .into(holder.photo)
        }

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        //LayoutInflater.from指定写法
        //        View itemView = View.inflate(context, R.layout.lv_item, null);
        context = parent.context
        val itemView = LayoutInflater.from(parent.context).inflate(com.testleancould.dodo.flashdemo.R.layout.photo_item, parent, false)
        return VH(itemView)
    }

    //重写这个方法，当item不可见时，清理掉imageview,避免oom
    override fun onViewRecycled(holder: VH) {
        super.onViewRecycled(holder)
        val imageView = holder.photo
        if (imageView != null) {
            context?.let { Glide.with(it).clear(imageView) }
        }
    }


    companion object {


        private val resultBeanItemCallback = object : DiffUtil.ItemCallback<Photo.ResultBean>() {
            override fun areItemsTheSame(
                oldItem: Photo.ResultBean,
                newItem: Photo.ResultBean
            ): Boolean {
                return false
            }

            override fun areContentsTheSame(
                oldItem: Photo.ResultBean,
                newItem: Photo.ResultBean
            ): Boolean {
                return false
            }
        }
    }
}
