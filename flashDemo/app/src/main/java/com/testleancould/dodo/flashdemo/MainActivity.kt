package com.testleancould.dodo.flashdemo

import android.graphics.Path
import android.os.Bundle
import android.view.WindowManager
import android.widget.Toast
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.adam.base.network.NetworkListener
import com.adam.base.network.core.NetType
import com.adam.base.network.core.Network
import kotlinx.android.synthetic.main.fragment_weather.*

class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"

    override fun  onCreate(savedInstanceState: Bundle?) {
        //使用闪屏优化，再切回原有主题
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        NetworkListener.instance?.registerObserver(this)


        //去掉状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)

        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        var path=Path()


        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications
            )
        )

        navView.setupWithNavController(navController)

    }



    @Network(netType = NetType.AUTO)
    fun onNetChanged(netType: NetType) {
        when(netType){
            NetType.WIFI->Toast.makeText(this,netType.name,Toast.LENGTH_LONG).show()
            NetType.CMNET->Toast.makeText(this,netType.name,Toast.LENGTH_LONG).show()
            NetType.CMWAP->Toast.makeText(this,netType.name,Toast.LENGTH_LONG).show()
            NetType.NONE->Toast.makeText(this,"没有网络",Toast.LENGTH_LONG).show()
        }
    }

   override fun onDestroy() {
        super.onDestroy()
        NetworkListener.instance?.unRegisterObserver(this)
    }

}
