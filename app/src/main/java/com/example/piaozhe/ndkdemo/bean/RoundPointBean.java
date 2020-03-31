package com.example.piaozhe.ndkdemo.bean;

import android.graphics.PointF;

/**
 * @author piaozhe
 * @date 2019/2/27.
 */
public class RoundPointBean {

    private PointF p1;//真是轨迹坐标点
    //起始点
    private PointF p2;//进入动画的点
    private PointF p3;//动画中心点
    private PointF p4;//移除动画的点
    private PointF p5;
    //结束点
    private PointF p;

    private float radiusValue;// 圆半径
    private float alphaValue;//透明度

    public RoundPointBean(PointF p1, PointF p2, PointF p3, PointF p4, PointF p5, float radiusValue, float alphaValue) {
        this.p1 = p1;
        this.p2 = p2;
        this.p3 = p3;
        this.p4 = p4;
        this.p5 = p5;
        this.radiusValue = radiusValue;
        this.alphaValue = alphaValue;
    }

    public PointF getP() {
        return p;
    }

    public void setP(PointF p) {
        this.p = p;
    }

    public PointF getP1() {
        return p1;
    }

    public void setP1(PointF p1) {
        this.p1 = p1;
    }

    public PointF getP2() {
        return p2;
    }

    public void setP2(PointF p2) {
        this.p2 = p2;
    }

    public PointF getP3() {
        return p3;
    }

    public void setP3(PointF p3) {
        this.p3 = p3;
    }

    public PointF getP4() {
        return p4;
    }

    public void setP4(PointF p4) {
        this.p4 = p4;
    }

    public PointF getP5() {
        return p5;
    }

    public void setP5(PointF p5) {
        this.p5 = p5;
    }

    public float getRadiusValue() {
        return radiusValue;
    }

    public void setRadiusValue(float radiusValue) {
        this.radiusValue = radiusValue;
    }

    public float getAlphaValue() {
        return alphaValue;
    }

    public void setAlphaValue(float alphaValue) {
        this.alphaValue = alphaValue;
    }
}
