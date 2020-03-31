package com.example.piaozhe.ndkdemo.jni.sign;

/**
 * @Author piaozhe
 * @Date 2020/3/25 16:25
 */
public class NativeSign {

    static {
        System.loadLibrary("nativesign");
    }



    public static native String getSecretStr();

}
