package com.testleancould.dodo.flashdemo.ui.notifications

import android.Manifest
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import pub.devrel.easypermissions.EasyPermissions
import android.app.Activity
import android.Manifest.permission
import android.Manifest.permission.ACCESS_NETWORK_STATE
import android.Manifest.permission.READ_PHONE_STATE
import android.Manifest.permission.CALL_PHONE
import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.R
import android.app.Person
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_notifications.*
import pub.devrel.easypermissions.AfterPermissionGranted


class NotificationsFragment : Fragment(),EasyPermissions.PermissionCallbacks {

    private lateinit var notificationsViewModel: NotificationsViewModel
    private lateinit var permissionBtn:Button
    private val TAG = "MainFragment"
    private val RC_SMS_PERM = 122
    private lateinit var dataTestBtn:Button


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        notificationsViewModel =
            ViewModelProviders.of(this).get(NotificationsViewModel::class.java)
        val root = inflater.inflate(com.testleancould.dodo.flashdemo.R.layout.fragment_notifications, container, false)
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