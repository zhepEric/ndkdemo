package com.example.piaozhe.ndkdemo.jni

/**
 *@Author piaozhe
 *@Date 2020/3/23 10:43
 */
class JniNative {

    companion object {
        init {
            System.loadLibrary("JniNative")
        }


    }


    external fun play(path:String)

}