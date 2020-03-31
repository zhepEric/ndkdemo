package com.example.piaozhe.ndkdemo

import android.media.MediaCodec
import android.support.v7.app.AppCompatActivity
import android.os.Bundle

class NativeActivity : AppCompatActivity() {

    external fun nativeCallJava()


    lateinit var mc :MediaCodec

    companion object{
        init {

            System.loadLibrary("native-lib")
        }

        //native层调用静态方法一定要声明@JvmStatic
        @JvmStatic
        fun stringToNative(): String{
            return "Native 调用 java 层"
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_native)


        nativeCallJava()

    }

    //这个方法可以在native层获取
    fun stringToNative1(): String{
        return "Native 调用 java 层"
    }

}
