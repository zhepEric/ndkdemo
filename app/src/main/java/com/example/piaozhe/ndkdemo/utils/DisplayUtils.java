package com.example.piaozhe.ndkdemo.utils;

import android.content.Context;
import android.graphics.Point;
import android.hardware.Camera;
import android.util.DisplayMetrics;
import android.util.Size;

import java.util.List;

/**
 * @author piaozhe
 * @date 2019/2/27.
 */
public class DisplayUtils {

    /**
     * 获取屏幕宽度
     * 默认按720*1280
     */

    public static int getDisplayWidth(Context context) {
        if (context != null) {

            DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
            return displayMetrics.widthPixels;
        }
        return 720;
    }
    /**
     * 获取屏幕高度
     */

    public static int getDisplayHeight(Context context){
        if (context != null){
            DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
            return displayMetrics.heightPixels;
        }
        return 1280;
    }


    //解决相机预览缩放问题
    public static Point getBestCameraResolution(Camera.Parameters parameters, Point screenResolution){
        float tmp = 0f;
        float mindiff = 100f;
        float x_d_y = (float)screenResolution.x / (float)screenResolution.y;
        Camera.Size best = null;
        List<Camera.Size> supportedPreviewSizes = parameters.getSupportedPreviewSizes();
        for(Camera.Size s : supportedPreviewSizes){
            tmp = Math.abs(((float)s.height/(float)s.width)-x_d_y);
            if(tmp<mindiff){
                mindiff = tmp;
                best = s;
            }
        }
        return new Point(best.width, best.height);
    }

}
