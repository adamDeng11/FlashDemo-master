package com.testleancould.dodo.flashdemo;

import android.app.Application;

import com.adam.base.network.NetworkListener;

/**
 * Created by adamDeng on 2019/11/13
 * Copyright © 2019年 . All rights reserved.
 */

public class App extends Application {
    @Override
    public void onCreate(){
        super.onCreate();
        NetworkListener.Companion.getInstance().init(this);
    }


}
