package com.testleancould.dodo.flashdemo.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.view.menu.MenuView
import androidx.recyclerview.widget.RecyclerView
import com.testleancould.dodo.flashdemo.R
import com.testleancould.dodo.flashdemo.bean.Chat
import kotlinx.android.synthetic.main.chat_item.view.*

/**
 * Created by adamDeng on 2019/11/5
 * Copyright © 2019年 . All rights reserved.
 */
class ChatViewAdapter :RecyclerView.Adapter<ChatViewAdapter.ViewHolder>{

    //    上下文
    private var context: Context? = null

    //    对话列表
    private var mlist: List<Chat>? = null

    constructor(context: Context,list: List<Chat>){
        this.context=context
        this.mlist=list
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.chat_item,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val chat=mlist!![position]
        if (chat.type==0){
            holder.leftLayout.visibility = View.VISIBLE
            holder.rightLayout.visibility = View.GONE
//            把文本设置到机器人对话框内
            holder.leftChat.setText(chat.text)
        }else if (chat.type==1){
            holder.leftLayout.visibility = View.GONE
            holder.rightLayout.visibility = View.VISIBLE
//            把文本设置到机器人对话框内
            holder.rightChat.setText(chat.text)
        }

    }

    override fun getItemCount(): Int {
        return mlist!!.size
    }

    class ViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){
        var leftLayout: LinearLayout
        var rightLayout: LinearLayout
        var leftChat: TextView
        var rightChat: TextView

        init {
            leftLayout=itemView.findViewById(R.id.left_layout)
            leftLayout = itemView.findViewById(R.id.left_layout)
            rightLayout = itemView.findViewById(R.id.right_layout)
            leftChat = itemView.findViewById(R.id.tv_left_text)
            rightChat = itemView.findViewById(R.id.tv_right_text)
        }
    }

}