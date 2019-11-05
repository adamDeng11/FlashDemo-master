package com.testleancould.dodo.flashdemo.bean

/**
 * Created by adamDeng on 2019/11/5
 * Copyright © 2019年 . All rights reserved.
 */
/**
 * data 数据类，默认帮我们进行了实体类的处理，例如toString,赋值
 * text 聊天文本数据
 * type 用来标示此对话类型是属于左边机器人发来的文本类型还是我们向机器人发送的文本类型，
 * 后面我们就根据这个属性标识来判断是左边的机器人消息还是右边的用户消息
 */
data class Chat(var text: String,var type: Int)