package com.example.piaozhe.ndkdemo.opengl

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.piaozhe.ndkdemo.R

class OpenglActivity : AppCompatActivity() {

    lateinit var myOpengl: MyOpengl
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        myOpengl = MyOpengl(this)
        setContentView(myOpengl)
    }
}
