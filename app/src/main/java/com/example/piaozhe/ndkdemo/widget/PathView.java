package com.example.piaozhe.ndkdemo.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import org.jetbrains.annotations.Nullable;

/**
 * 贝塞尔曲线应用场景：
 * 1、事先不知道曲线的变化规则，如天气预报气温变化平滑折线图
 * 2、用户手势操作，改变图形状态，QQ小红点，翻书效果
 * 3、复杂运动状态动效
 * @author piaozhe
 * @date 2019/2/21.
 */
public class PathView extends View {
    private Paint paint;
    private PointF start, end, control;
    private int centerX;
    private int centerY;
    public PathView(Context context) {
        super(context);
    }

    public PathView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs,0);
        paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(30);

        start = new PointF(0,0);
        end = new PointF(0,0);
        control = new PointF(0,0);
    }

    public PathView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        centerX = w/2;
        centerY = h/2;
        start.x = centerX - 200;
        start.y = centerY - 200;
        end.x = centerX + 200;
        end.y = centerY +200;

        control.x = centerX;
        control.y = centerY;

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        control.x = event.getX();
        control.y = event.getY();
        invalidate();
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Path path = new Path();
        path.moveTo(start.x, start.y);
        //二阶贝塞尔
        path.quadTo(control.x,control.y, end.x, end.y);

        //三阶贝塞尔
//        path.cubicTo();

        canvas.drawPath(path, paint);
    }
}
