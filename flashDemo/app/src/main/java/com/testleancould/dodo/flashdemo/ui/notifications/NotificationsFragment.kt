package com.testleancould.dodo.flashdemo.ui.notifications

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import pub.devrel.easypermissions.EasyPermissions
import android.Manifest.permission
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.load.resource.bitmap.VideoDecoder
import com.shuyu.gsyvideoplayer.utils.OrientationUtils
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer
import com.testleancould.dodo.flashdemo.R


class NotificationsFragment : Fragment(),EasyPermissions.PermissionCallbacks {

    private lateinit var notificationsViewModel: NotificationsViewModel
    private lateinit var permissionBtn:Button
    private val TAG = "MainFragment"
    private val RC_SMS_PERM = 122
    private lateinit var dataTestBtn:Button

    private lateinit var videoPlayer: StandardGSYVideoPlayer

    internal var orientationUtils: OrientationUtils? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        notificationsViewModel =
            ViewModelProviders.of(this).get(NotificationsViewModel::class.java)
        val root = inflater.inflate(com.testleancould.dodo.flashdemo.R.layout.fragment_notifications, container, false)

        videoPlayer=root.findViewById(com.testleancould.dodo.flashdemo.R.id.video_player)
        var source="http://9890.vod.myqcloud.com/9890_4e292f9a3dd011e6b4078980237cc3d3.f20.mp4"
        videoPlayer.setUp(source,true,"小鬼视频")
        //添加封面
        var imageView=ImageView(context)
        imageView.scaleType=ImageView.ScaleType.CENTER
        imageView.setImageResource(R.mipmap.photo_error)
        videoPlayer.thumbImageView=imageView
        //添加标题
        videoPlayer.titleTextView.visibility=View.VISIBLE
        //添加返回键
        videoPlayer.backButton.visibility=View.VISIBLE
        /*//设置旋转
        orientationUtils=OrientationUtils(this,videoPlayer)*/
        //设置全屏按键功能，这里用的是选择屏幕，而并不是全屏
        /*videoPlayer.fullscreenButton.setOnClickListener{
            orientationUtils!!.resolveByClick()
        }*/
        //是否可以滑动
        videoPlayer.setIsTouchWiget(true)




        permissionBtn=root.findViewById(com.testleancould.dodo.flashdemo.R.id.btn_permission)
        dataTestBtn=root.findViewById(com.testleancould.dodo.flashdemo.R.id.btn_dataTest)
        permissionBtn.setOnClickListener { smsTask()
        }

        return root
    }
    /*检查权限是否存在，不存在就请求*/
    private fun smsTask(){

        if (EasyPermissions.hasPermissions(context, permission.READ_SMS)) {
            // Have permission, do the thing!
            Toast.makeText(activity, "权限已经存在", Toast.LENGTH_LONG).show()
        } else {
            // Request one permission
            EasyPermissions.requestPermissions(
                this, "请求SMS权限",
                RC_SMS_PERM, permission.READ_SMS
            )
            Log.i("ssss","sss")
        }
    }


    /*用于接收请求结果*/
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode,permissions,grantResults,this)
    }
    /*请求成功*/
    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
    /*请求失败*/
    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }



}