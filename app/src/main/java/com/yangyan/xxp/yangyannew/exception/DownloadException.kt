package com.yangyan.xxp.yangyannew.exception

/**
 * Author : zhongwenpeng
 * Email : 1340751953@qq.com
 * Time :  2018/5/24
 * Description :下载报的异常
 */
class DownloadException(val e: String) : RuntimeException(e)