package com.example.piaozhe.ndkdemo.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import org.jetbrains.annotations.Nullable;

/**
 * @author piaozhe
 * @date 2019/2/20.
 */
public class MyView extends View {
    private Paint paint;
    private int top;
    private int right;
    private int left;
    private int bottom;
    private Canvas canvas;
    private int[] colors = {Color.RED,Color.GREEN, Color.BLUE,Color.BLACK};
    private int[] cornersTo = {90,90, 90,90};
    public MyView(Context context) {
        super(context);
        initView();
    }

    private void initView() {


        top = 100;
        right = 200;
        left = 50;
        bottom = 200;
    }

    public MyView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs,0);
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.BLUE);
    }

    public MyView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        int top = getTop();
//        int left = getLeft();
//        int right = getRight();
//        Log.i("onDraw","---top=="+top);

        RectF rectF = new RectF();
        rectF.top = 100;
        rectF.left = 50;
        rectF.right = 200;
        rectF.bottom = 300;
        canvas.drawRect(rectF,paint);//绘制矩形

        paint.setColor(Color.RED);
        paint.setStrokeWidth(10);//绘制画笔宽度
        //1、绘制点
        canvas.drawPoint(30,30,paint);
        canvas.drawPoints(new float[]{300,50,300,100, 300,150},paint);

        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(5);
        //2、绘制线,前两个参数是起始点，后两位是结束点
        canvas.drawLine(50,50,150,50,paint);
        canvas.drawLines(new float[]{
                350,50,400,50,
                350,70,400,70
        },paint);

        //3、绘制圆角矩形
        paint.setColor(Color.GRAY);

        //rx,ry表示椭圆夹角的xy半径
        canvas.drawRoundRect(50,400,200,500,10,10,paint);


        //
        RectF rectDraw = new RectF(50,550,400,600);

        canvas.drawRect(rectDraw,paint);
        paint.setColor(Color.GREEN);
        //当rx,与ry都大于等于矩形的宽高的一半时，刚好形成一个椭圆
        canvas.drawRoundRect(rectDraw,600,200,paint);

        //4、绘制椭圆
        RectF rectOval = new RectF(50,650,350,800);
        canvas.drawOval(rectOval,paint);

        paint.setStyle(Paint.Style.STROKE);//设置为线
        canvas.drawOval(new RectF(400,650,800,800),paint);


        //5、绘制圆
        //cx,cy是圆心坐标，radius是半径
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.RED);
        canvas.drawCircle(100,900,50,paint);

        //6、绘制圆弧
        RectF rectArc = new RectF(50,1000,250,1200);
        RectF rectArc1 = new RectF(300,1000,500,1200);
        RectF rectArc2 = new RectF(550,1000,750,1200);
        RectF rectArc3 = new RectF(800,1000,1000,1200);
        paint.setColor(Color.BLUE);
        //startAngle 起始角度为0度，sweepAngle扫过的角度为90度,useCenter 是否是中心点
        canvas.drawArc(rectArc,0,90,false,paint);
        canvas.drawArc(rectArc1,0,90,true,paint);
        paint.setStyle(Paint.Style.STROKE);

        canvas.drawArc(rectArc2,0,90,true,paint);
        canvas.drawArc(rectArc3,0,90,false,paint);

        //绘制饼图
        drawPieChart(canvas);
    }

    private void drawPieChart(Canvas canvas) {
        int currentStart=cornersTo[0];
        paint.setStyle(Paint.Style.FILL);
        RectF rectF = new RectF(50,1300,250,1500);
        for (int i = 0; i < colors.length; i++) {

            paint.setColor(colors[i]);
            canvas.drawArc(rectF,currentStart,cornersTo[i],true,paint);
            currentStart +=cornersTo[i];
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }

    /**
     * 确定最终的控件大小
     * @param w
     * @param h
     * @param oldw
     * @param oldh
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float getY = event.getY();
        float rawY = event.getRawY();
        Log.i("onTouchEvent","----getY="+getY+"----getRawY="+rawY);
        return super.onTouchEvent(event);
    }
}
