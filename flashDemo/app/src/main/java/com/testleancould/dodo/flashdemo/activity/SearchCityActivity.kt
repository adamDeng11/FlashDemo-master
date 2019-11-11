package com.testleancould.dodo.flashdemo.activity

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.MenuItem
import android.widget.SearchView
import android.widget.Switch
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.OrientationHelper
import androidx.recyclerview.widget.RecyclerView
import com.testleancould.dodo.flashdemo.MainActivity
import com.testleancould.dodo.flashdemo.R
import com.testleancould.dodo.flashdemo.adapter.CityAdapter
import com.testleancould.dodo.flashdemo.bean.BasicCity
import com.testleancould.dodo.flashdemo.bean.CityBean
import com.testleancould.dodo.flashdemo.net.CityService
import com.testleancould.dodo.flashdemo.ui.decoration.ItemDecoration
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class SearchCityActivity : AppCompatActivity() {

    private lateinit var searchView:SearchView
    private lateinit var cityRecyclerView: RecyclerView
    private lateinit var cityAdapter: CityAdapter
    private lateinit var data:ArrayList<BasicCity>
    private var retrofit = Retrofit.Builder()  //创建Retrofit实例
        .baseUrl("https://search.heweather.net/")    //这里需要传入url的域名部分
        .addConverterFactory(GsonConverterFactory.create()) //返回的数据经过转换工厂转换成我们想要的数据，最常用的就是Gson
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()   //构建实例
    private var service: CityService =retrofit.create(CityService::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppThemeBack)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_city)

        var actionBar=supportActionBar
        actionBar?.setHomeButtonEnabled(true)
        actionBar?.setDisplayHomeAsUpEnabled(true)

        init()

        cityAdapter.setOnItemClickListener(object :CityAdapter.OnItemClick{
            override fun onItemClick(position: Int) {
                var intent=Intent()
                intent.setClass(this@SearchCityActivity,MainActivity::class.java)
                intent.putExtra("data_return",data[position].cid)
                setResult(Activity.RESULT_OK,intent)
                finish()

            }
        })

        searchView.setOnQueryTextListener(object :SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String): Boolean {

                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                if (!TextUtils.isEmpty(newText)) {
                    data.clear()
                    requestCity(newText)

                } else {

                }
                return false
            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        when{
            item!!.itemId==android.R.id.home->this.finish()
        }

        return super.onOptionsItemSelected(item)
    }

    fun init(){
        searchView=findViewById(R.id.searchView)
        cityRecyclerView=findViewById(R.id.recycleView_city)
        data=ArrayList()
        cityAdapter=CityAdapter(this,data)
        cityRecyclerView.adapter=cityAdapter
        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = RecyclerView.VERTICAL
        cityRecyclerView.layoutManager=linearLayoutManager
        let { ItemDecoration(it, OrientationHelper.VERTICAL) }?.let {
            cityRecyclerView.addItemDecoration(
                it
            )
        }

    }

    fun requestCity(location: String){
        val call = service!!.getCity(location,"288367e25c264a1bb63aff12da05e278","cn")
        call.enqueue(object : Callback<CityBean> {
            override fun onResponse(call: Call<CityBean>, response: Response<CityBean>) {
                val bean = response.body()
                val basic= bean!!.HeWeather6[0].basic
                if (basic != null) {
                    data.addAll(bean.HeWeather6[0].basic)
                    cityAdapter.notifyDataSetChanged()
                }else{
                    Toast.makeText(this@SearchCityActivity,"城市不存在",Toast.LENGTH_LONG).show()
                }

            }

            override fun onFailure(call: Call<CityBean>, t: Throwable) {
                Toast.makeText(this@SearchCityActivity,"请检查网络",Toast.LENGTH_LONG).show()
            }
        })
    }
}
