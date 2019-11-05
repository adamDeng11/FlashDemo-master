package com.testleancould.dodo.flashdemo.activity

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.testleancould.dodo.flashdemo.R

/**
 * Created by adamDeng on 2019/10/21
 * Copyright © 2019年 . All rights reserved.
 */
class NewsItemActivity:AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_item)

        var bundle=intent.getBundleExtra("data")
        var position=bundle.getString("position")

        var newsItemTxt=findViewById<TextView>(R.id.txt_news_item)
        newsItemTxt.text="第"+position+"条"

    }

}


