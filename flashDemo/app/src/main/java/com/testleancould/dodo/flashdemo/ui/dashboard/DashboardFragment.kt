package com.testleancould.dodo.flashdemo.ui.dashboard

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.testleancould.dodo.flashdemo.GlideApp

import android.os.Message

import android.annotation.SuppressLint
import com.testleancould.dodo.flashdemo.R


class DashboardFragment : Fragment() {

    private lateinit var dashboardViewModel: DashboardViewModel
    private lateinit var testBtn: Button
    private lateinit var testImage: ImageView
    private val CHANGE_TEXT = 1
    private val handler = @SuppressLint("HandlerLeak")
    object : Handler() {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            when (msg.what) {
                CHANGE_TEXT ->
                    //在这里可以进行UI操作
                    GlideApp.with(this@DashboardFragment).load("http://p1.pstatp.com/large/166200019850062839d3").error(
                        R.mipmap.head_show).fitCenter().into(testImage)

                else -> {
                }
            }
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dashboardViewModel =
            ViewModelProviders.of(this).get(DashboardViewModel::class.java)
        val view = inflater.inflate(R.layout.fragment_dashboard, container, false)
        testBtn=view.findViewById(R.id.btn_test2)
        testImage=view.findViewById(R.id.image_test2)

        testBtn.setOnClickListener {
            Thread(Runnable {
                //新建一个Message对象，存储需要发送的消息
                val message = Message()
                message.what = CHANGE_TEXT
                //然后将消息发送出去
                handler.sendMessage(message)
            }).start()

            }

        return view
    }



}