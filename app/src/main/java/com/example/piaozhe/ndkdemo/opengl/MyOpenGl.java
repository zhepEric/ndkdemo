package com.example.piaozhe.ndkdemo.opengl;

import android.content.Context;
import android.media.AudioRecord;
import android.media.AudioTrack;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;
import android.view.MotionEvent;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * @Author piaozhe
 * @Date 2020/3/27 15:58
 */
public class MyOpenGl extends GLSurfaceView{


    private MyRender myRender;
    AudioRecord audioRecord;

    public MyOpenGl(Context context) {
        super(context);
    }

    public MyOpenGl(Context context, AttributeSet attrs) {
        super(context, attrs);

        init();
    }

    private void init() {
        myRender = new MyRender();
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {


        return super.onTouchEvent(event);
    }

    class MyRender implements GLSurfaceView.Renderer{

        float angle = 0f;
        @Override
        public void onSurfaceCreated(GL10 gl, EGLConfig config) {

        }

        @Override
        public void onSurfaceChanged(GL10 gl, int width, int height) {

        }

        @Override
        public void onDrawFrame(GL10 gl) {

        }
    }
}
