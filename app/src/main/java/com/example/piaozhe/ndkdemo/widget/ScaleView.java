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
public class ScaleView extends View {
    public ScaleView(Context context) {
        super(context);
    }

    public ScaleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs,0);
    }

    public ScaleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        canvas.translate(getWidth()/2,getHeight()/2);

        paint.setColor(Color.BLUE);
        //缩放比例0-1，sx、sy设置0不会显示,设置1没有变化
        for (int i = 0; i < 20; i++) {
            canvas.scale(0.9f,0.9f);
            canvas.drawRect(new RectF(-400,-400,400,400),paint);
        }
    }
}
