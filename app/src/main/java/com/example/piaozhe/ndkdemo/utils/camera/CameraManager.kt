package com.example.piaozhe.ndkdemo.utils.camera

import android.hardware.Camera
import android.os.Environment
import android.view.SurfaceHolder
import android.view.SurfaceView
import java.io.File
import java.io.FileOutputStream
import java.io.IOException


/**
 *@Author piaozhe
 *@Date 2020/3/19 15:18
 */
class CameraManager : ICameraManager {



    lateinit var camera:Camera
    companion object {

        fun getCameraInfo(): String {
            //获取摄像头个数
            val numberOfCameras = Camera.getNumberOfCameras()
//            Camera.getCameraInfo()

            return ""
        }
    }

    //打开相机
    override fun open(cameraId: Int): Camera {

        if (cameraId != 0) {
            camera = Camera.open(cameraId)
        } else {
            camera = Camera.open()
        }

        return camera
    }

    //开始预览
    override fun startPreview(surfaceHolder: SurfaceHolder?) {

        //配置参数

        val parameters = camera.parameters


        camera.parameters = parameters
        camera.startPreview()
    }


    //拍照
    /**
     * takePicture方法参数
     * ShutterCallback shutter：在拍照的瞬间被回调，这里通常可以播放"咔嚓"这样的拍照音效。
    PictureCallback raw：返回未经压缩的图像数据。
    PictureCallback postview：返回postview类型的图像数据
    PictureCallback jpeg：返回经过JPEG压缩的图像数据。

     */
    override fun takePicker() {


        camera.takePicture(null,null,object :Camera.PictureCallback{
            override fun onPictureTaken(data: ByteArray?, camera: Camera?) {
                val eDirectory = Environment.getExternalStorageDirectory()
                val file:File = eDirectory.absoluteFile

                if (!file.exists()){
                    val files = File(file.toString())
                    files.mkdirs()
                }
                try {
                    val fileOutputStream = FileOutputStream("$file/pick.jpg")

                    fileOutputStream.write(data)
                    fileOutputStream.close()

                }catch (e:IOException){

                }
            }

        })
    }


    //关闭预览
    override fun stopPreview() {
        camera.stopPreview()
    }

    //关闭相机
    override fun close() {
        camera?.let {
            it.release()
        }
    }
}