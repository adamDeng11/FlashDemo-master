package com.testleancould.dodo.flashdemo.bean

/**
 * Created by adamDeng on 2019/10/11
 * Copyright © 2019年 深圳市云歌人工智能技术有限公司. All rights reserved.
 */
class Photo {

    /**
     * code : 200
     * message : 成功!
     * result : [{"id":429,"time":"2018-09-18 17:52:42","img":"http://ww4.sinaimg.cn/large/7a8aed7bjw1f2tpr3im0mj20f00l6q4o.jpg"},{"id":77,"time":"2018-09-18 17:52:39","img":"http://7xi8d6.com1.z0.glb.clouddn.com/20171212083612_WvLcTr_Screenshot.jpeg"},{"id":415,"time":"2018-09-18 17:52:41","img":"http://ww4.sinaimg.cn/large/7a8aed7bgw1etdsksgctqj20hs0qowgy.jpg"},{"id":282,"time":"2018-09-18 17:52:41","img":"http://ww3.sinaimg.cn/large/610dc034jw1f9rc3qcfm1j20u011hmyk.jpg"},{"id":570,"time":"2018-09-18 17:52:42","img":"http://ww2.sinaimg.cn/large/7a8aed7bgw1evx488twblj20qo0iddjt.jpg"},{"id":542,"time":"2018-09-18 17:52:42","img":"http://ww1.sinaimg.cn/large/7a8aed7bjw1exe9ssy2gsj20qo0hndjr.jpg"},{"id":198,"time":"2018-09-18 17:52:40","img":"http://7xi8d6.com1.z0.glb.clouddn.com/2017-03-23-17265820_645330569008169_4543676027339014144_n.jpg"},{"id":527,"time":"2018-09-18 17:52:42","img":"http://ww4.sinaimg.cn/large/7a8aed7bjw1ey2lc2h2ckj20o20gxacp.jpg"},{"id":525,"time":"2018-09-18 17:52:42","img":"http://ww1.sinaimg.cn/large/7a8aed7bjw1ey4w5cdjbej20hs0qoq7q.jpg"},{"id":419,"time":"2018-09-18 17:52:41","img":"http://ww3.sinaimg.cn/large/7a8aed7bjw1f39v1uljz8j20c50hst9q.jpg"},{"id":63,"time":"2018-09-18 17:52:39","img":"http://ww1.sinaimg.cn/large/0065oQSqly1frepsy47grj30qo0y97en.jpg"},{"id":497,"time":"2018-09-18 17:52:42","img":"http://ww1.sinaimg.cn/large/7a8aed7bjw1ezf3wrxcx2j20p011i7b2.jpg"},{"id":438,"time":"2018-09-18 17:52:42","img":"http://ww3.sinaimg.cn/large/7a8aed7bjw1f2cfxa9joaj20f00fzwg2.jpg"},{"id":1,"time":"2018-09-18 17:52:39","img":"https://ws1.sinaimg.cn/large/0065oQSqly1fv5n6daacqj30sg10f1dw.jpg"},{"id":612,"time":"2018-09-18 17:52:43","img":"http://ww2.sinaimg.cn/large/7a8aed7bgw1eu0w2kjjr9j20hs0qoq6w.jpg"},{"id":178,"time":"2018-09-18 17:52:40","img":"http://7xi8d6.com1.z0.glb.clouddn.com/2017-04-24-18094714_158946097967074_5909424912493182976_n.jpg"},{"id":532,"time":"2018-09-18 17:52:42","img":"http://ww3.sinaimg.cn/large/a3bcec5fjw1exukiyu2zoj20hs0qo0w9.jpg"},{"id":130,"time":"2018-09-18 17:52:39","img":"http://ww1.sinaimg.cn/large/610dc034ly1fhxe0hfzr0j20u011in1q.jpg"},{"id":418,"time":"2018-09-18 17:52:41","img":"http://ww2.sinaimg.cn/large/7a8aed7bjw1f3azi5zoysj20dw0kuabb.jpg"},{"id":660,"time":"2018-10-09 04:00:01","img":"https://ws1.sinaimg.cn/large/0065oQSqly1fw0vdlg6xcj30j60mzdk7.jpg"}]
     */
    var code: Int = 0
    var message: String? = null
    var result: List<ResultBean>? = null

    class ResultBean {
        /**
         * id : 429
         * time : 2018-09-18 17:52:42
         * img : http://ww4.sinaimg.cn/large/7a8aed7bjw1f2tpr3im0mj20f00l6q4o.jpg
         */

        var id: Int = 0
        var time: String? = null
        var img: String? = null
    }
}
