package com.testleancould.dodo.flashdemo.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.testleancould.dodo.flashdemo.R
import com.testleancould.dodo.flashdemo.bean.BasicCity
import java.util.*

/**
 * Created by adamDeng on 2019/10/28
 * Copyright © 2019年 . All rights reserved.
 */
class CityAdapter(private val context: Context, private val data: ArrayList<BasicCity>) :
    RecyclerView.Adapter<CityAdapter.VH>() {
    private lateinit var onItemClick: OnItemClick

    class VH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal val name: TextView = itemView.findViewById(R.id.city_name)

    }

    override fun onBindViewHolder(holder: VH, position: Int) {

        holder.name.text= data[position].location+","+data[position].parent_city+","+data[position].admin_area+","+data[position].cnty
        holder.name.setOnClickListener { onItemClick.onItemClick(position) }

    }

    fun setOnItemClickListener(onItemClick:OnItemClick){
        this.onItemClick=onItemClick
    }

    interface OnItemClick {
        fun onItemClick(position: Int)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val itemView = LayoutInflater.from(context).inflate(R.layout.city_item, parent, false)
        return VH(itemView)
    }


}