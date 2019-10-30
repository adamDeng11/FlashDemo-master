package com.testleancould.dodo.flashdemo.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import androidx.fragment.app.Fragment
import com.testleancould.dodo.flashdemo.R
import kotlinx.android.synthetic.main.fragment_joke.*
import android.webkit.WebViewClient



/**
 * Created by adamDeng on 2019/10/9
 * Copyright © 2019年 深圳市云歌人工智能技术有限公司. All rights reserved.
 */
class JokeFragment : Fragment(){

    lateinit var mapWebview: WebView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view=inflater.inflate(R.layout.fragment_joke, container, false)
        mapWebview=view.findViewById(R.id.webView_map)
        mapWebview.settings.javaScriptEnabled=true
        mapWebview.webViewClient= WebViewClient()
        mapWebview.loadUrl("https://map.qq.com/m/index/map")

        return view

    }
}
