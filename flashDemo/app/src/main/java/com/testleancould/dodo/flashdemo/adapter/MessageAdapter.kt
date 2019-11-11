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
import com.testleancould.dodo.flashdemo.R
import com.testleancould.dodo.flashdemo.bean.Message

/**
 * Created by adamDeng on 2019/10/9
 * Copyright © 2019年 深圳市云歌人工智能技术有限公司. All rights reserved.
 */


//创建构造函数
class MessageAdapter : PagedListAdapter<Message.ResultBean, MessageAdapter.VH>(resultBeanItemCallback) {
    private var context: Context? = null
    private lateinit var onItemClick:OnItemClick
    //② 创建ViewHolder
    class VH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal var name: TextView
        internal var time: TextView
        internal var photo: ImageView

        init {
            name = itemView.findViewById(R.id.news_name)
            time = itemView.findViewById(R.id.news_time)
            photo = itemView.findViewById(R.id.news_photo)

        }

    }

    fun setOnItemClickListener(onItemClick:OnItemClick){
        this.onItemClick=onItemClick
    }

    interface OnItemClick{
        fun onItemClick(position: Int)
    }

    //③ 在Adapter中实现3个方法
    override fun onBindViewHolder(holder: VH, position: Int) {


        val resultBean = getItem(position)
        holder.name.text = resultBean!!.title
        holder.time.text=resultBean!!.passtime
        context?.let {
            GlideApp.with(it).load(resultBean!!.image)
                .into(holder.photo)
        }

        holder.name.setOnClickListener { onItemClick.onItemClick(position) }

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        //LayoutInflater.from指定写法
        //        View itemView = View.inflate(context, R.layout.lv_item, null);
        context = parent.context
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.news_item, parent, false)
        return VH(itemView)
    }

    companion object {


        private val resultBeanItemCallback = object : DiffUtil.ItemCallback<Message.ResultBean>() {
            override fun areItemsTheSame(
                oldItem: Message.ResultBean,
                newItem: Message.ResultBean
            ): Boolean {
                return false
            }

            override fun areContentsTheSame(
                oldItem: Message.ResultBean,
                newItem: Message.ResultBean
            ): Boolean {
                return false
            }
        }
    }
}
