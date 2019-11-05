package com.testleancould.dodo.flashdemo.bean

/**
 * Created by adamDeng on 2019/11/5
 * Copyright © 2019年 . All rights reserved.
 */

/*
 * robot请求参数
 *
 */
data class Ask(
    val perception: Perception,
    val reqType: Int,
    val userInfo: UserInfo
)

data class Perception(
    val inputText: InputText
)


data class InputText(
    val text: String
)

data class UserInfo(
    val apiKey: String,
    val userId: String
)