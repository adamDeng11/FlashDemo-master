package com.testleancould.dodo.flashdemo.test;

/**
 * Created by adamDeng on 2019/11/14
 * Copyright © 2019年 . All rights reserved.
 */
public class AnnotationTest {

    @GET(value = "123")
    public String getIp1(){
        return "";
    }
    @GET(value = "123456")
    public String getIp2(String value){
        return value;
    }

}
