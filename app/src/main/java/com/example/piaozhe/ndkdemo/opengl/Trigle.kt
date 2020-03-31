package com.example.piaozhe.ndkdemo.opengl

import android.opengl.GLES20
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer

/**
 *@Author piaozhe
 *@Date 2020/3/27 14:31
 *
 * 限定符：
 * attritude：一般用于各个顶点各不相同的量。如顶点颜色、坐标等。
 * uniform:一般用于对于3D物体中所有顶点都相同的量。比如光源位置，统一变换矩阵等。
 * precision:
 * varying:表示易变量，一般用于顶点着色器传递到片元着色器的量。
 *
 * 顶点着色器的内建变量:输入变量
 * gl_Position:设置顶点
 * gl_PointSize：点的大小，没有赋值则为默认值1，通常设置绘图为点绘制才有意义。\
 * 片元着色器的内建变量:
 * 输入变量
 * gl_FragFacing：bool型，表示是否为属于光栅化生成此片元的对应图元的正面。
 * gl_FragCoord：当前片元相对窗口位置所处的坐标。
 *
 * 输出变量
 * gl_FragColor：当前片元颜色
 * gl_FragData：vec4类型的数组。向其写入的信息，供渲染管线的后继过程使用。
 *
 */
class Trigle {

    //顶点着色器
    private val vertexShaderCode = """
       uniform mat4 uMVPMatrix;
       attribute vec4 vPosition;
       void main() {
       gl_Position = uMVPMatrix * vPosition;
       }
   """
    private val fragmentShaderCode = """
        precision mediump float;
        uniform vec4 vColor;
        void main() {
        gl_FragColor = vColor;
        }
    """.trimIndent()


    companion object {
        //定义三角形形状
        const val COORDS_PER_VERTEX = 3
        var triangleCoords = floatArrayOf(     // in counterclockwise order:
                0.0f, 0.622008459f, 0.0f,      // top
                -0.5f, -0.311004243f, 0.0f,    // bottom left
                0.5f, -0.311004243f, 0.0f      // bottom right
        )
    }

    // Set color with red, green, blue and alpha (opacity) values
    val color = floatArrayOf(0.63671875f, 0.76953125f, 0.22265625f, 1.0f)

    private var vertexBuffer: FloatBuffer =
            // (number of coordinate values * 4 bytes per float)
            ByteBuffer.allocateDirect(triangleCoords.size * 4).run {
                // use the device hardware's native byte order
                order(ByteOrder.nativeOrder())

                // create a floating point buffer from the ByteBuffer
                asFloatBuffer().apply {
                    // add the coordinates to the FloatBuffer
                    put(triangleCoords)
                    // set the buffer to read the first coordinate
                    position(0)
                }
            }


    var mProgram: Int

    var posHandle: Int = 0
    var colorHandle: Int = 0


    var vertexCount = triangleCoords.size / COORDS_PER_VERTEX
    var vertexStrict: Int = COORDS_PER_VERTEX * 4

    private var vPMatrixHandle: Int = 0

    init {

        val vertextShader = loadShader(GLES20.GL_VERTEX_SHADER, vertexShaderCode)
        val fragmentShader = loadShader(GLES20.GL_FRAGMENT_SHADER, fragmentShaderCode)

        mProgram = GLES20.glCreateProgram().also {

            GLES20.glAttachShader(it, vertextShader)

            GLES20.glAttachShader(it, fragmentShader)

            GLES20.glLinkProgram(it)
        }

    }

    fun loadShader(type: Int, shaderColor: String): Int {
        return GLES20.glCreateShader(type).also { shader ->
            run {
                // add the source code to the shader and compile it
                GLES20.glShaderSource(shader, shaderColor)
                GLES20.glCompileShader(shader)
            }
        }
    }



    fun draw(mvpMatrix: FloatArray) {
        //添加mProgram到OPGL引擎
        GLES20.glUseProgram(mProgram)

        posHandle = GLES20.glGetAttribLocation(mProgram, "vPosition").also {

            GLES20.glEnableVertexAttribArray(it)


            //
            GLES20.glVertexAttribPointer(
                    it,
                    COORDS_PER_VERTEX,
                    GLES20.GL_FLOAT,
                    false,
                    vertexStrict,
                    vertexBuffer
            )


            colorHandle = GLES20.glGetUniformLocation(mProgram, "vColor").also {
                GLES20.glUniform4fv(colorHandle, 1, color, 0)
            }

            //防止横竖屏切换导致图像拉伸
            vPMatrixHandle = GLES20.glGetUniformLocation(mProgram, "uMVPMatrix")
            GLES20.glUniformMatrix4fv(vPMatrixHandle, 1, false, mvpMatrix, 0)

            //绘制三角形
            GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, vertexCount)

            GLES20.glDisableVertexAttribArray(it)
        }

    }
}