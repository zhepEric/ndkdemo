package com.example.piaozhe.ndkdemo.utils.camera


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.piaozhe.ndkdemo.R

/**
 * 处理实际交互逻辑，与UI展示层分离
 */
class CameraFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_camera, container, false)
    }


}
