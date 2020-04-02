package com.example.piaozhe.ndkdemo.media

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.piaozhe.ndkdemo.R

class AudioActivity : AppCompatActivity() {

    val audioManager:AudioRecordManager by lazy { AudioRecordManager() }
    val PERMISSIONS = arrayOf(Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_audio)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

    }

    fun onMediaRecord(view: View) {
        //判断6.0动态权限
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED ||
                    checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

                requestPermissions(PERMISSIONS, 1)
            } else {
                audioManager.startRecord()

            }
        } else {
            audioManager.startRecord()

        }
    }

    fun onMediaRecordStop(view: View) {
        audioManager.stop()
    }


}
