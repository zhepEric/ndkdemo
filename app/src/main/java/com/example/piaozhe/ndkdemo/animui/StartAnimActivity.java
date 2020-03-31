package com.example.piaozhe.ndkdemo.animui;

import android.graphics.PointF;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;

import com.example.piaozhe.ndkdemo.R;
import com.example.piaozhe.ndkdemo.bean.RoundPointBean;
import com.example.piaozhe.ndkdemo.utils.DisplayUtils;
import com.example.piaozhe.ndkdemo.widget.animation.StartView;

import java.util.ArrayList;
import java.util.List;

public class StartAnimActivity extends AppCompatActivity {

    private StartView startView;
    private List<RoundPointBean> pointBeanList = new ArrayList<>();
    private RoundPointBean rp1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_anim);
        startView = findViewById(R.id.start_view);
        initData();
        startView.setRoundPointsList(pointBeanList);
        startView.setRoundPoints(rp1);
    }

    private void initData() {
        int width = DisplayUtils.getDisplayWidth(StartAnimActivity.this);
        int height = DisplayUtils.getDisplayHeight(StartAnimActivity.this);
        int centerX = width / 2;
        int centerY = height / 2;
//        rp1 = new RoundPointBean(new PointF(displayWidth/5,displayHeight/2)
//        ,new PointF(centerX - 30,centerY)
//        ,new PointF(width,height)
//        ,new PointF(width,height)
//        ,new PointF(width,height)
//        ,width/2
//        ,0.5f);

        //起始点
        rp1 = new RoundPointBean(
                new PointF((float) (-width / 5.1), (float) (height / 1.5)),
                new PointF(centerX - 30, height * 2 / 3),
                new PointF((float) (width / 2.4), (float) (height / 3.4)),
                new PointF(width / 6, centerY - 120),
                new PointF((float) (width / 7.2), -height / 128),
                (float) (width / 14.4), 60);
        RoundPointBean rp2 = new RoundPointBean(
                new PointF(-width / 4, (float) (height / 1.3)),
                new PointF(centerX - 20, height * 3 / 5),
                new PointF((float) (width / 2.1), (float) (height / 2.5)),
                new PointF(width / 3, centerY - 10),
                new PointF(width / 4, (float) (-height / 5.3)),
                width / 4, 60);
        RoundPointBean rp3 = new RoundPointBean(
                new PointF(-width / 12, (float) (height / 1.1)),
                new PointF(centerX - 100, height * 2 / 3),
                new PointF((float) (width / 3.4), height / 2),
                new PointF(0, centerY + 100),
                new PointF(0, 0),
                width / 24, 60);

        RoundPointBean rp4= new RoundPointBean(
                new PointF(-width / 9, (float) (height / 0.9)),
                new PointF(centerX, height * 3 / 4),
                new PointF((float) (width / 2.1), (float) (height / 2.3)),
                new PointF(width / 2, centerY),
                new PointF((float) (width / 1.5), (float) (-height / 5.6)),
                width / 4, 60);
        RoundPointBean rp5 = new RoundPointBean(
                new PointF((float) (width / 1.4), (float) (height / 0.9)),
                new PointF(centerX, height * 3 / 4),
                new PointF(width / 2, (float) (height / 2.37)),
                new PointF(width * 10 / 13, centerY - 20),
                new PointF(width / 2, (float) (-height / 7.1)),
                width / 4, 60);
        RoundPointBean rp6 = new RoundPointBean(
                new PointF((float) (width / 0.8), height),
                new PointF(centerX + 20, height * 2 / 3),
                new PointF((float) (width / 1.9), (float) (height / 2.3)),
                new PointF(width * 11 / 14, centerY + 10),

                new PointF((float) (width / 1.1), (float) (-height / 6.4)),
                (float) (width / 4), 60);

        RoundPointBean rp7 = new RoundPointBean(
                new PointF((float) (width / 0.9), (float) (height / 1.2)),
                new PointF(centerX + 20, height * 4 / 7),
                new PointF((float) (width / 1.6), (float) (height / 1.9)),
                new PointF(width, centerY + 10),

                new PointF(width, 0),
                (float) (width / 9.6), 60);

        pointBeanList.add(rp1);
        pointBeanList.add(rp2);
        pointBeanList.add(rp3);
        pointBeanList.add(rp4);
        pointBeanList.add(rp5);
        pointBeanList.add(rp6);
        pointBeanList.add(rp7);

    }

    public void startAnimOnClick(View view) {

        startView.startAnim();
    }
}
