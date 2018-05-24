/*
 * Copyright 2017 JessYan
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.yangyan.xxp.yangyannew.app

import android.content.Context
import android.net.ParseException
import cn.bmob.v3.exception.BmobException

import com.google.gson.JsonIOException
import com.google.gson.JsonParseException
import com.jess.arms.utils.ArmsUtils
import es.dmoral.toasty.Toasty

import org.json.JSONException

import java.net.SocketTimeoutException
import java.net.UnknownHostException

import me.jessyan.rxerrorhandler.handler.listener.ResponseErrorListener
import retrofit2.HttpException
import timber.log.Timber

/**
 * ================================================
 * 展示 [ResponseErrorListener] 的用法
 *
 *
 * Created by JessYan on 04/09/2017 17:18
 * [Contact me](mailto:jess.yan.effort@gmail.com)
 * [Follow me](https://github.com/JessYanCoding)
 * ================================================
 *
 * Modify by zhongwenpeng
 * 2018-5-24
 */
class ResponseErrorListenerImpl : ResponseErrorListener {

    override fun handleResponseError(context: Context, t: Throwable) {
        Timber.tag("Catch-Error").w(t.message)
        //这里不光只能打印错误, 还可以根据不同的错误做出不同的逻辑处理
        //这里只是对几个常用错误进行简单的处理, 展示这个类的用法, 在实际开发中请您自行对更多错误进行更严谨的处理
        val msg = when (t) {
            is UnknownHostException -> "网络不可用"
            is SocketTimeoutException -> "请求网络超时"
            is HttpException -> convertStatusCode(t)
            is BmobException -> convertStatusCode(t)
            is JsonParseException, is ParseException, is JSONException, is JsonIOException -> "数据解析错误"
            else -> "未知错误"
        }
        Toasty.error(context, msg).show()
    }

    private fun convertStatusCode(httpException: HttpException): String {
        return when (httpException.code()) {
            500 -> "服务器发生错误"
            404 -> "请求地址不存在"
            403 -> "请求被服务器拒绝"
            307 -> "请求被重定向到其他页面"
            202 -> "用户名已经存在"
            203 -> "邮箱已经存在"
            else -> httpException.message()
        }

    }

    private fun convertStatusCode(bmobException: BmobException): String {
        return when (bmobException.errorCode) {
            109 -> "无效用户信息"
            202,203 -> "用户已经存在"
            else -> bmobException.message ?: "未知错误"
        }

    }
}
