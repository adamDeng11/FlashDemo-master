package com.testleancould.dodo.flashdemo.test;

/**
 * Created by adamDeng on 2019/11/5
 * Copyright © 2019年 . All rights reserved.
 */
/*//饿汉式（静态常量）
public class Singleton {

    private Singleton(){};
    private final static Singleton instance=new Singleton();
    public static Singleton getInstance(){
        return instance;
    }
}*/
/*//饿汉式（静态代码块）
public class Singleton {

    private Singleton(){};
    private final static Singleton instance;
    static {
        instance=new Singleton();
    }
    public static Singleton getInstance(){
        return instance;
    }
}*/

/*//懒汉式(线程不安全)
class Singleton{

    private static Singleton instance;
    private Singleton(){}

    public static Singleton getInstance(){
        if (instance==null){
            instance=new Singleton();
        }
        return instance;
    }

}*/

/*//懒汉式（线程安全，同步方法）
class Singleton{

    private static Singleton instance;
    private Singleton(){}

    public static synchronized Singleton getInstance(){
        if (instance==null){
            instance=new Singleton();
        }
        return instance;
    }

}*/


//懒汉式（线程不安全，同步代码块）x


//双重检查（推荐）
class Singleton{
    private static volatile Singleton instance;
    private Singleton(){}
    //解决了线程安全，解决效率低，实现懒加载
    public static Singleton getInstance(){
        if (instance==null){
            synchronized(Singleton.class){
                if (instance==null){
                    instance=new Singleton();
                }
            }
        }

        return instance;
    }
}

/*//静态内部类（推荐）
class Singleton{
    private static volatile Singleton singleton;

    private Singleton(){}

    private static class SingletonInstance{
        private static final Singleton SINGLETON=new Singleton();
    }

    public static synchronized Singleton getSingleton(){
        return SingletonInstance.SINGLETON;
    }
}*/

/*
//枚举(推荐)避免多线程同步问题，还能防止反序列化重新创建的对象
enum Singleton{
    //属性
    INSTANCE;
    public void sayOk(){

    }
}*/
