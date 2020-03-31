package com.example.piaozhe.ndkdemo.utils.camera;

import android.hardware.Camera;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * @Author piaozhe
 * @Date 2020/3/19 15:13
 */
public interface ICameraManager {

    //开启
    public abstract Camera open(int cameraId) ;

    //开始预览

    void startPreview(SurfaceHolder surfaceHolder);

    //拍照
    void takePicker();

    //结束预览
    void stopPreview();
    //关闭
    void close();
}
