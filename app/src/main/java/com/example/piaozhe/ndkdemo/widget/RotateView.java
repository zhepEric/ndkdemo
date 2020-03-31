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
 * @date 2019/2/20.
 */
public class RotateView extends View {
    public RotateView(Context context) {
        super(context);
    }

    public RotateView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public RotateView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Paint paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.BLACK);

        RectF rectF = new RectF(0,-200,200,0);
        canvas.translate(getWidth()/2,getHeight()/2);
        canvas.drawRect(rectF,paint);
        //顺时针旋转180度，旋转中心向右偏移100个点
        canvas.rotate(180,100,0);

        paint.setColor(Color.BLUE);
        canvas.drawRect(rectF,paint);
    }
}
