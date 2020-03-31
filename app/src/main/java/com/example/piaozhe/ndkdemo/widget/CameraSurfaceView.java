package com.example.piaozhe.ndkdemo.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.SurfaceView;

/**
 * @Author piaozhe
 * @Date 2020/3/20 16:46
 */
public class CameraSurfaceView extends SurfaceView {
    public CameraSurfaceView(Context context) {
        super(context);
    }

    public CameraSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    //设置宽高比例固定4：3
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);

        int height = width / 3 * 4;

        setMeasuredDimension(width,height);
    }
}
