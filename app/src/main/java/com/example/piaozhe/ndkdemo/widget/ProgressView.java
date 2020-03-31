package com.example.piaozhe.ndkdemo.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import org.jetbrains.annotations.Nullable;

/**
 * @author piaozhe
 * @date 2019/2/21.
 */
public class ProgressView extends View {
    private Paint paint;
    private RectF whiteRect;
    private int left = 100;
    private int top = 100;
    private int right = 700;
    private int bottom = 200;
    private int firstRadius = 0;


    public ProgressView(Context context) {
        super(context);
    }

    public ProgressView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs,0);
        paint = new Paint();
        whiteRect = new RectF(left+ (bottom - top)/2, top, right, bottom);

    }

    public ProgressView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        firstRadius = (bottom - top)/2;
        canvas.drawColor(0Xaafdce4f);

        paint.setColor(0X55ffffff);
        //绘制白色半透明底部半圆
        canvas.drawArc(new RectF(100,100,200,200),90,180,false,paint);

        //绘制白色半透明底部矩形
        canvas.drawRect(whiteRect,paint);

        //绘制白色圆形风车底部背景
        paint.setColor(Color.WHITE);
        canvas.drawCircle(right, top + firstRadius, firstRadius,paint);
    }
}
