package com.example.piaozhe.ndkdemo.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.media.AudioTrack;
import android.util.AttributeSet;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * @Author piaozhe
 * @Date 2020/3/18 17:35
 *SurfaceView:
 * 1.它拥有独立的特殊的绘制表面，即 它不与其宿主窗口共享一个绘制表面
 * 2.SurefaceView的UI可以在一个独立的线程中进行绘制
 * 3.因为不会占用主线程资源，一方面可以实现复杂而高效的UI，二是不会导致用户输入得不到及时响应。
 * 4.具有双缓冲机制
 */

/**
 * SurfaceView是一个有自己Surface的View。界面渲染可以放在单独线程而不是主线程中。它更像是一个Window，自身不能做变形和动画。
 * TextureView同样也有自己的Surface。但是它只能在拥有硬件加速层层的Window中绘制，它更像是一个普通View，可以做变形和动画。
 *
 * SurfaceHolder用于控制Surface的一个抽象接口，它可以控制Surface的尺寸、格式与像素等，并可以监视Surface的变化。
 * SurfaceHolder.Callback：用于监听Surface状态变化的接口。
 */


public class MySurfaceView extends SurfaceView implements SurfaceHolder.Callback ,Runnable{

    Surface surface;
    Canvas canvas;
    AudioTrack audioTrack;
    public MySurfaceView(Context context) {
        super(context);
        init();
    }



    public MySurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();

    }

    public MySurfaceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();

    }

    private void init() {

        SurfaceHolder holder = getHolder();
        holder.addCallback(this);


    }

    /**
     * 当Surface第一次创建的时候调用，可以在这个方法里调用camera.open()、camera.setPreviewDisplay()来实现打开相机以及连接Camera与Surface 等操作。
     * @param holder
     */
    @Override
    public void surfaceCreated(SurfaceHolder holder) {

        Thread thread = new Thread(this);
        thread.start();
    }

    /**
     * 当Surface的size、format等发生变化的时候调用，可以在这个方法里调用camera.startPreview()开启预览。
     * @param holder
     * @param format
     * @param width
     * @param height
     */
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    /**
     * 当Surface被销毁的时候调用，可以在这个方法里调用camera.stopPreview()，camera.release()等方法来实现结束预览以及释放
     * @param holder
     */
    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }


    //执行耗时操作
    @Override
    public void run() {

        drawView();
    }

    private void drawView() {


    }
}
