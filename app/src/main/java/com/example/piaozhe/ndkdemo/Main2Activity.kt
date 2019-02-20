package com.example.piaozhe.ndkdemo

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.piaozhe.ndkdemo.widget.MyView

class Main2Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)


    }

    override fun onPause() {
        super.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

}
