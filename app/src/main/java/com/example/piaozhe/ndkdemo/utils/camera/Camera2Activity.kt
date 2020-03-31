package com.example.piaozhe.ndkdemo.utils.camera

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.ImageFormat
import android.graphics.drawable.GradientDrawable
import android.hardware.camera2.*
import android.hardware.camera2.CameraManager
import android.media.ImageReader
import android.os.*
import android.support.v7.app.AppCompatActivity
import android.support.v4.app.ActivityCompat
import android.util.Log
import android.util.SparseIntArray
import android.view.Surface
import android.view.SurfaceHolder
import android.view.View
import com.example.piaozhe.ndkdemo.R
import com.example.piaozhe.ndkdemo.utils.DisplayUtils
import kotlinx.android.synthetic.main.activity_camera3.*
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.lang.Exception
import java.nio.ByteBuffer
import java.security.Permission

class Camera2Activity : AppCompatActivity() {

    lateinit var holder: SurfaceHolder
    val PERMISSIONS = arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
    lateinit var mainHandler: Handler
    lateinit var childHandler: Handler

    val FRONT_CAMERA = "1"
    val BACK_CAMERA = "0"

    var cameraId = "0"
    lateinit var cameraDevice: CameraDevice //摄像头，类似android.hardware.Camera也就是Camera1的Camera
    lateinit var imageReader: ImageReader

    lateinit var reqB: CaptureRequest.Builder
    lateinit var cameraCaptureSession: CameraCaptureSession //这个类控制摄像头的预览或者拍照

    val ORIENTATIONS = SparseIntArray()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera3)

        initView()
        initData()
    }

    private fun initData() {
        //为了使照片竖直显示
        ORIENTATIONS.append(Surface.ROTATION_0,90)
        ORIENTATIONS.append(Surface.ROTATION_90,0)
        ORIENTATIONS.append(Surface.ROTATION_180,270)
        ORIENTATIONS.append(Surface.ROTATION_270,180)

    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

    }

    private fun initView() {
        holder = surface_view.holder

        holder.addCallback(object : SurfaceHolder.Callback {
            override fun surfaceChanged(holder: SurfaceHolder?, format: Int, width: Int, height: Int) {

                Log.i("SurfaceHolder.Callback","-----------surfaceChanged=w=$width==h=$height")
            }

            override fun surfaceDestroyed(holder: SurfaceHolder?) {
            }

            override fun surfaceCreated(holder: SurfaceHolder?) {
                //判断6.0动态权限
                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
                    if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED ||
                            checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

                        requestPermissions(PERMISSIONS, 1)
                    } else {
                        initCamera2()
                    }
                } else {
                    initCamera2()
                }
            }

        })

    }

    private fun initCamera2() {

        val handlerThread = HandlerThread("camera2")
        handlerThread.start()
        mainHandler = Handler(mainLooper) //获得主线程Looper
        childHandler = Handler(handlerThread.looper)


        initCameraReader()
        var cameraManager = getSystemService(Context.CAMERA_SERVICE) as CameraManager

//        adjustScreen(surface_view)
        var stateCallback = object : CameraDevice.StateCallback() {
            override fun onOpened(camera: CameraDevice) {
                cameraDevice = camera
                startPreview()
                Log.i("StateCallback", "--------onOpened")
            }

            override fun onDisconnected(camera: CameraDevice) {
            }

            override fun onError(camera: CameraDevice, error: Int) {
            }

        }

        try {

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                return
            }
            cameraManager.openCamera(cameraId, stateCallback, mainHandler)

        } catch (e: CameraAccessException) {

        }

    }

    //调整预览宽高比
    private fun adjustScreen(view: View) {
        val w = view.width
        val h = view.height

        if (h > w){
            val scale = w * 4.0F/3
            surface_view.scaleX = h / scale
        }else{

            val scaleY = h * 4.0F/3
            surface_view.scaleY = w / scaleY
        }
    }


    //初始化ImageReader，以及设置拍照图片临时文件
    private fun initCameraReader() {
        imageReader = ImageReader.newInstance(1080, 1920, ImageFormat.JPEG, 1)

        imageReader.setOnImageAvailableListener({

            val nextImage = it.acquireNextImage()

            val buffer:ByteBuffer = nextImage.planes[0].buffer

            var b = ByteArray(buffer.remaining())

            buffer.get(b)
            var fos: FileOutputStream? = null
            val absolutePath = Environment.getExternalStorageDirectory().absolutePath
            val filePath = absolutePath + "/camera" + System.currentTimeMillis() + ".jpg"
            val file = File(filePath)

            try {
                fos = FileOutputStream(file)
                fos.write(b)
                fos.flush()

            } catch (e: IOException) {

                e.printStackTrace()
            } finally {

                try {
                    fos?.let {
                        fos.close()
                    }
                } catch (e: IOException) {
                    e.printStackTrace()
                }


                startPreview()
                nextImage.close()
            }
        }
                , mainHandler)
    }


    //预览
    fun startPreview() {
        // 创建预览需要的CaptureRequest.Builder
        reqB = cameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW)
// 将SurfaceView的surface作为CaptureRequest.Builder的目标
        reqB.addTarget(holder.surface)
        val arrayOf = mutableListOf<Surface>(holder.surface, imageReader.surface)
        cameraDevice.createCaptureSession(arrayOf, camerastate, childHandler)

    }

    val camerastate = object : CameraCaptureSession.StateCallback() {
        override fun onConfigureFailed(session: CameraCaptureSession) {
        }

        override fun onConfigured(session: CameraCaptureSession) {
            cameraCaptureSession = session

            //自动对焦
            reqB.set(CaptureRequest.CONTROL_AF_MODE, CaptureRequest
                    .CONTROL_AF_MODE_CONTINUOUS_PICTURE)

            //开始预览
            session.setRepeatingRequest(reqB.build(), null, childHandler)
        }

    }


    /**
     * CaptureRequest：表示一次捕获请求，用来对照片的各种参数设置，比如对焦模式、曝光模式等。
     *
     */
    //拍照
    fun startPick() {

        cameraDevice?.let {
            try {

                //创建拍照请求
                var reqBuilder = cameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_STILL_CAPTURE)
                reqBuilder.addTarget(imageReader.surface)

                //自动对焦
                reqBuilder.set(CaptureRequest.CONTROL_AF_MODE, CaptureRequest
                        .CONTROL_AF_MODE_CONTINUOUS_PICTURE)

                //闪光灯
                reqBuilder.set(CaptureRequest.FLASH_MODE,CaptureRequest.FLASH_STATE_READY)


                //选择方向，否则会拍出选择90°的图片
                val rotation = windowManager.defaultDisplay.rotation
                reqBuilder.set(CaptureRequest.JPEG_ORIENTATION,ORIENTATIONS.get(rotation))

                cameraCaptureSession.capture(reqBuilder.build(), null, childHandler)

            } catch (e: CameraAccessException) {

                e.printStackTrace()
            }
        }
    }

    fun takePick(view: View) {
        startPick()
    }

    
    private fun setPreviewSize() {
        
    }
}
