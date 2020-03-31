package com.example.piaozhe.ndkdemo

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import android.view.Window
import android.widget.ListView
import com.example.piaozhe.ndkdemo.widget.MyView
import kotlinx.android.synthetic.main.activity_main2.*

class Main2Activity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        var myView = MyView(Main2Activity@this)


        button_click.setOnClickListener { startActivity(Intent(Main2Activity@this,ViewActivity::class.java)) }
        progress_btn.setOnClickListener { startActivity(Intent(Main2Activity@this,ProgressActivity::class.java)) }
        quadTo_btn.setOnClickListener { startActivity(Intent(Main2Activity@this,PathQuadToActivity::class.java)) }
        button_click1.setOnClickListener { startActivity(Intent(Main2Activity@this,NativeActivity::class.java)) }

    }

    override fun onPause() {
        super.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

}
