package com.example.piaozhe.ndkdemo.opengl

import android.content.Context
import android.opengl.GLES20
import android.opengl.GLSurfaceView
import android.opengl.Matrix
import android.os.SystemClock
import android.view.MotionEvent
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

/**
 *@Author piaozhe
 *@Date 2020/3/27 14:01
 *
 * 绘制图形涉及到
 * 顶点着色器：负责定义绘制的图形形状
 * 片元着色器:负责设置图形的颜色
 *
 * GLSL语言
 * 数据类型主要分为标量、向量、矩阵、采样器、结构体、数组、空类型七种类型：
 * 标量：只有bool、int和float三种
 * 向量：共有vec2、vec3、vec4，ivec2、ivec3、ivec4、bvec2、bvec3和bvec4九种类型，其中i代表int，b代表bool
 *矩阵：矩阵拥有22、33、4*4三种类型的矩阵，分别用mat2、mat3、mat4表示，可以把矩阵看成二维数组
 *
 */
class MyOpengl(context: Context) : GLSurfaceView(context) {

    var myGlRender: MyGlRender = MyGlRender()

    init {

        setEGLContextClientVersion(2)




        setRenderer(myGlRender)

    }

    lateinit var trigle: Trigle

    // vPMatrix is an abbreviation for "Model View Projection Matrix"
    private val vPMatrix = FloatArray(16)
    //投影矩阵,之后可以将其与 onDrawFrame() 方法中的相机视图转换合并
    private val projectionMatrix = FloatArray(16)
    private val viewMatrix = FloatArray(16)

    //旋转矩阵
    private val rotationMatrix = FloatArray(16)

    companion object {
        //手势
        private const val TOUCH_SCALE_FACTOR: Float = 180.0f / 320f
        private var previousX: Float = 0f
        private var previousY: Float = 0f
    }


    //实现手势触发图形旋转
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        var pX = event?.x
        var pY = event?.y

        when (event?.action) {
            MotionEvent.ACTION_MOVE -> {
                var dX = pX?.minus(previousX)
                var dY = pY?.minus(previousY)

                pY?.let {
                    if (dX != null) {
                        if (it > height / 2) {

                            dX *= -1
                        }
                    }
                }

                pX?.let {
                    if (dY != null) {
                        if (it < width / 2) {
                            dY *= -1
                        }
                    }
                }

                if (dX != null) {
                    myGlRender.angle +=(dX + dY!!) * TOUCH_SCALE_FACTOR
                }
                requestRender()

            }
        }

        previousX = pX!!
        previousY = pY!!

        return true
    }


    inner class MyGlRender : Renderer {

        @Volatile
        var angle: Float = 0F
        var age: Int = 0

        override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {

            GLES20.glClearColor(1.0f, 0.0f, 0.0f, 1.0f)
            //在此初始化要绘制的图形，

            trigle = Trigle()
        }

        override fun onDrawFrame(gl: GL10?) {
            val scratch = FloatArray(16)

            GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT)

            //解决图形拉伸问题
            Matrix.setLookAtM(viewMatrix, 0, 0f, 0f, -3f, 0f, 0f, 0f, 0f, 1.0f, 0.0f)
            Matrix.multiplyMM(vPMatrix, 0, projectionMatrix, 0, viewMatrix, 0)

            //支持旋转的图形
//            val time = SystemClock.uptimeMillis() % 4000L
//            val angle = 0.090f * time.toInt()
            Matrix.setRotateM(rotationMatrix, 0, angle, 0f, 0f, -1.0f)

            Matrix.multiplyMM(scratch, 0, vPMatrix, 0, rotationMatrix, 0)

            trigle.draw(scratch)
        }

        override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {

            GLES20.glViewport(0, 0, width, height)

            val ratio = width.toFloat() / height.toFloat()

            //定义投影转换Matrix

            Matrix.frustumM(projectionMatrix, 0, -ratio, ratio, -1f, 1f, 3f, 7f)
        }


    }
}