package com.testleancould.dodo.flashdemo.ui.dashboard

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.testleancould.dodo.flashdemo.GlideApp

import android.os.Message

import android.annotation.SuppressLint
import android.os.TestLooperManager
import android.text.TextUtils
import android.util.Log
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.testleancould.dodo.flashdemo.R
import com.testleancould.dodo.flashdemo.adapter.ChatViewAdapter
import com.testleancould.dodo.flashdemo.bean.*
import com.testleancould.dodo.flashdemo.net.RobotService
import kotlinx.android.synthetic.main.fragment_dashboard.*
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


class DashboardFragment : Fragment() {

    private lateinit var dashboardViewModel: DashboardViewModel
    private lateinit var list:ArrayList<Chat>
    private lateinit var sendBtn:Button
    private lateinit var input:EditText
    private lateinit var recyclerView:RecyclerView
    private lateinit var chatViewAdapter: ChatViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dashboardViewModel = ViewModelProviders.of(this).get(DashboardViewModel::class.java)
        val view = inflater.inflate(R.layout.fragment_dashboard, container, false)
        list= ArrayList()
        sendBtn=view.findViewById(R.id.btn_send)
        input=view.findViewById(R.id.edt_messageInput)
        recyclerView=view.findViewById(R.id.recycleView_robot)

        chatViewAdapter= context?.let { ChatViewAdapter(it,list) }!!
        recyclerView.adapter=chatViewAdapter

        val linearLayoutManager=LinearLayoutManager(context)
        recyclerView.layoutManager=linearLayoutManager
        linearLayoutManager.orientation=RecyclerView.VERTICAL

        sendBtn.setOnClickListener { init() }
        return view
    }

    /**
     * 设置控件的回调监听
     */

    private fun init(){
        var text:String=input.text.toString()
        if (!TextUtils.isEmpty(text)){
            addData(text,1)
            request(text)
        }else{
            Log.i("内容为空","空")
        }

    }

    private fun addData(text:String,type:Int){
        var char=Chat(text,type)
        list.add(char)
        //局部刷新
        chatViewAdapter.notifyItemChanged(list.size-1)
        //定位到最后一条
        recycleView_robot.scrollToPosition(list.size-1)
    }

    private fun request(text: String){
        var perception=Perception(InputText(text))
        val userInfo=UserInfo("c00282de107144fb940adab994d9ff98","225167")
        var ask=Ask(perception,0,userInfo)

         var retrofit = Retrofit.Builder()//创建Retrofit实例
            .baseUrl("http://openapi.tuling123.com/") //这里需要传入url的域名部分
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())//返回的数据经过转换工厂转换成我们想要的数据，最常用的就是Gson
            .build()
        val robot=retrofit.create(RobotService::class.java)
        val  call=robot.getRobot(ask)

        call.enqueue(object :retrofit2.Callback<Robot>{
            override fun onResponse(call: Call<Robot>, response: Response<Robot>) {
                var text=response.body()!!.results[0].values.text
                if (text==null){
                    text="去问度娘吧"
                    addData(text,0)
                }else{
                    addData(text,0)
                }
            }

            override fun onFailure(call: Call<Robot>, t: Throwable) {
                Log.i("请求失败",t.toString())
            }
        })


    }


}