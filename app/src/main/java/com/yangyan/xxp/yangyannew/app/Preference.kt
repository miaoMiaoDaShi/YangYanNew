package com.yangyan.xxp.yangyannew.app

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty


/**
 * Author : zhongwenpeng
 * Email : 1340751953@qq.com
 * Time :  2018/2/26
 * Description :
 */

class Preference<T>(private val key: String, private val default: T) : ReadWriteProperty<Any, T> {
    companion object {
        lateinit var sharedPreferences: SharedPreferences
        fun init(context: Context, name: String = "config") {
            sharedPreferences = context.getSharedPreferences(name, Context.MODE_PRIVATE)
        }

        fun clean() {
            sharedPreferences.edit().clear().commit()
        }
    }


    override fun getValue(thisRef: Any, property: KProperty<*>): T = get(key, default)

    private fun get(key: String, default: T): T =
            with(sharedPreferences) {
                val res: Any = when (default) {
                    is Long -> getLong(key, default)
                    is String -> getString(key, default)
                    is Int -> getInt(key, default)
                    is Boolean -> getBoolean(key, default)
                    is Float -> getFloat(key, default)
                    else -> throw IllegalArgumentException("This type can be saved into Preferences")
                }
                res as T
            }


    override fun setValue(thisRef: Any, property: KProperty<*>, value: T) = put(key, value)

    @SuppressLint("ApplySharedPref")
    private fun put(key: String, value: T) {
        sharedPreferences.edit().apply {
            when (value) {
                is Long -> putLong(key, value)
                is String -> putString(key, value)
                is Int -> putInt(key, value)
                is Boolean -> putBoolean(key, value)
                is Float -> putFloat(key, value)
                else -> throw IllegalArgumentException("This type can be saved into Preferences")
            }

        }.commit()
    }
}