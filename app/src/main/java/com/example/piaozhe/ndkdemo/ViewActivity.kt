package com.example.piaozhe.ndkdemo

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_view.*

class ViewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view)
        rotateBtn.setOnClickListener { startActivity(Intent(ViewActivity@this, RotateActivity::class.java)) }
    }

    fun animationBtn(view: View){
        startActivity(Intent(ViewActivity@this, AnimActivity::class.java))
    }
}
