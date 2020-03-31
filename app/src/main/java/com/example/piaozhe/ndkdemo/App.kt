package com.example.piaozhe.ndkdemo

import android.app.Application
import android.content.Context
import kotlin.properties.Delegates

/**
 *@Author piaozhe
 *@Date 2020/3/31 17:34
 */
class App:Application() {


    companion object{

        @JvmStatic
        var instance :Context by  Delegates.notNull<Context>()
    }

    override fun onCreate() {
        super.onCreate()
        instance = applicationContext
    }
}