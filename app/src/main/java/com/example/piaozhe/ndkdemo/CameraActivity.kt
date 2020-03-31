package com.example.piaozhe.ndkdemo

import android.graphics.Camera
import android.support.v7.app.AppCompatActivity
import android.os.Bundle

/**
 *
 * @property camera Camera
 */
class CameraActivity : AppCompatActivity() {

    lateinit var camera:Camera
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera)
    }
}
