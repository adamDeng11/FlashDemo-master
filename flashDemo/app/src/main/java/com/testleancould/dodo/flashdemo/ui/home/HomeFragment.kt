package com.testleancould.dodo.flashdemo.ui.home

import android.graphics.Color
import android.os.Bundle
import android.os.Message
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.testleancould.dodo.flashdemo.R
import com.testleancould.dodo.flashdemo.adapter.MessageAdapter
import com.testleancould.dodo.flashdemo.ui.fragment.JokeFragment
import com.testleancould.dodo.flashdemo.ui.fragment.NewsFragment
import com.testleancould.dodo.flashdemo.ui.fragment.PhotoFragment
import com.testleancould.dodo.flashdemo.ui.fragment.WeatherFragment
import com.testleancould.dodo.flashdemo.viewmodel.MessageViewModel
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_news.*
import java.util.ArrayList

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel


    private lateinit var myTab: TabLayout
    private lateinit var viewPager:ViewPager


    //fragment初始化
    private var photoFragment = PhotoFragment()
    private var newsFragment=NewsFragment()
    private var weatherFragment=WeatherFragment()
    private var jokeFragment=JokeFragment()

    private var mFragment: List<Fragment> = listOf(photoFragment,
        newsFragment, weatherFragment, jokeFragment)
    var titles = listOf("美图", "新闻", "天气", "笑话")


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProviders.of(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)

        myTab=root.findViewById(R.id.my_tab)
        myTab.setSelectedTabIndicatorColor(resources.getColor(R.color.colorPrimaryDark))
        //tab下划线颜色
        myTab.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {



            }

            override fun onTabUnselected(tab: TabLayout.Tab) {

            }

            override fun onTabReselected(tab: TabLayout.Tab) {

            }
        })
        viewPager=root.findViewById(R.id.mViewPager)
        //fragment嵌套fragment要用childFragmentManager获得fragment管理器
        viewPager.adapter = object : FragmentPagerAdapter(childFragmentManager) {
            override fun getItem(position: Int): Fragment {
                return mFragment[position]
            }

            override fun getCount(): Int {
                return mFragment.size
            }

            override fun getPageTitle(position: Int): CharSequence? {
                return titles.get(position)
            }
        }

        myTab.setupWithViewPager(viewPager)



        return root

    }



}