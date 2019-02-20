package com.example.piaozhe.ndkdemo

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {


    lateinit var bitmap: Bitmap
    lateinit var createBitmapOut: Bitmap
     var isColor:Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bitmap = BitmapFactory.decodeResource(resources, R.drawable.avatar)
        // Example of a call to a native method
        sample_text.text = stringFromJNI()
        Log.i("Main","--add--"+addValue(5,10))
//        stringJNITest( "java 传入 native层")

        imageView.setImageBitmap(bitmap)

        createBitmapOut = Bitmap.createBitmap(
                bitmap.width,
                bitmap.height,
                Bitmap.Config.ARGB_8888
        )

        button.setOnClickListener {  imageView.setImageBitmap(setBitmap(bitmap))}
        button2.setOnClickListener {

            if (!isColor){
                isColor = true

                setWhiteBlackBitmap(bitmap, createBitmapOut)
                imageView.setImageBitmap(createBitmapOut)
            }else{

                isColor = false
                imageView.setImageBitmap(bitmap)
            }
        }
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     * kotlin中采用external 关键字调用native方法
     */
    external fun stringFromJNI(): String
//    external fun stringJNITest(value:String):String
    external fun backStringToJava():String
    external fun getPersonValue():String
    external fun addValue(a:Int,b:Int):Int
    external fun setBitmap(bitmap:Bitmap):Bitmap
    external fun setWhiteBlackBitmap(bitmapIn: Bitmap,bitmapOut:Bitmap)
    companion object {

        // Used to load the 'native-lib' library on application startup.
        init {
            System.loadLibrary("native-lib")
        }
    }
}
