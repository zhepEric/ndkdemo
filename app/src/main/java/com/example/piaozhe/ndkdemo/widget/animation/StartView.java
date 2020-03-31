package com.example.piaozhe.ndkdemo.widget.animation;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.example.piaozhe.ndkdemo.bean.RoundPointBean;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * @author piaozhe
 * @date 2019/2/27.
 */
public class StartView extends View {

    private RoundPointBean roundPointBean;
    private Paint paint;
    private AnimatorSet animatorSet;
    private List<RoundPointBean> roundPointBeanList = new ArrayList<>();
    private List<PathMeasure> pathMeasureList = new ArrayList<>();
    private float circleRadius = 20f;

    public StartView(Context context) {
        super(context);
        init();
    }

    private void init() {
        animatorSet = new AnimatorSet();
        paint = new Paint();
        paint.setColor(Color.RED);
    }

    public StartView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public StartView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void setRoundPointsList(List<RoundPointBean> pointBeanList) {

        this.roundPointBeanList = pointBeanList;
    }

    public List<RoundPointBean> getRoundPointBeanList() {
        return roundPointBeanList;
    }

    public void setRoundPoints(RoundPointBean rp1) {
        roundPointBean = rp1;
    }

    public RoundPointBean getRoundPointBean() {
        return roundPointBean;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Log.i("StartView","-------onDraw----");

        if (animatorSet.isStarted()){

            if (roundPointBeanList != null){
//                float x = roundPointBean.getP1().x;
//                Log.i("StartView","-------onDraw----x="+x);
                for (RoundPointBean roundPointBean: getRoundPointBeanList()) {
                    Log.i("StartView","-------onDraw----getP="+roundPointBean.getP().x);

                    canvas.drawCircle(roundPointBean.getP().x, roundPointBean.getP().y, roundPointBean.getRadiusValue(), paint);
                }

            }
        }

    }

    public void startAnim() {

        if (animatorSet.isRunning()){
//            animatorSet.play(floatAnimation());
            animatorSet.playTogether(floatAnimation());
            animatorSet.start();
        }
    }

    private ValueAnimator initAnimation() {

        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0,1);

        return valueAnimator;
    }

    private ValueAnimator floatAnimation() {

        final float[] pos = new float[2];

        for (int i = 0; i < roundPointBeanList.size(); i++) {
            Path path = new Path();

            if (i % 2 == 0){

                path.addCircle(roundPointBeanList.get(i).getP3().x,roundPointBeanList.get(i).getP3().y,circleRadius,Path.Direction.CCW);
            }else {
                path.addCircle(roundPointBeanList.get(i).getP3().x,roundPointBeanList.get(i).getP3().y,circleRadius,Path.Direction.CW);

            }

            PathMeasure pathMeasure = new PathMeasure();
            pathMeasure.setPath(path, true);
            pathMeasureList.add(pathMeasure);
        }

        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0,1);
        valueAnimator.setDuration(3000);
        valueAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float animatedValue = (float) animation.getAnimatedValue();

                for (int i = 0; i < roundPointBeanList.size(); i++) {
                    pathMeasureList.get(i).getPosTan(pathMeasureList.get(i).getLength() * animatedValue, pos, null);
                    roundPointBeanList.get(i).setP(new PointF(pos[0],pos[1]));
                }

                invalidate();

            }
        });
        
        return valueAnimator;
    }
}
