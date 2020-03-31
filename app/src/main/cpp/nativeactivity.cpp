//
// Created by ptlive on 2020/3/13.
//

#include "include/nativeactivity.h"
#include <jni.h>
#include <string>
#include <android/log.h>

#define TAG "JNI_"
#define LOGD(...) __android_log_print(ANDROID_LOG_DEBUG,TAG,__VA_ARGS__)

//JNICALL void native_call_Java(JNIEnv *env, jobject thiz);

//动态注册NativeActivity中调用方法
//jint registerMethodNativeActivity(JNIEnv *env) {
//    jclass clz = env->FindClass("com/example/piaozhe/ndkdemo/NativeActivity");
//    if (NULL == clz) {
//        LOGD("can't find class: com/example/piaozhe/ndkdemo/NativeActivity");
//
//    }
//
//    JNINativeMethod jniNativeMethod[] = {
//            {"nativeCallJava", "()V", (void*)native_call_Java}
//    };
//
//    return env -> RegisterNatives(clz, jniNativeMethod, sizeof(jniNativeMethod) / sizeof(jniNativeMethod[0]));
//}



//JNI 调用JAVA层静态方法
extern "C"

JNICALL void
Java_com_example_piaozhe_ndkdemo_NativeActivity_nativeCallJava(JNIEnv *env, jobject thiz) {
    jclass object = env->FindClass("com/example/piaozhe/ndkdemo/NativeActivity");

    if (NULL == object) {
        return;
    }
    //获取java层静态方法
    jmethodID staticMethodId = env->GetStaticMethodID(object, "stringToNative",

                                                      "()Ljava/lang/String;");

    //获取java层方法
    jmethodID methodId = env->GetMethodID(object, "stringToNative1",

                                                      "()Ljava/lang/String;");

    if (NULL == staticMethodId) {
        LOGD("获取方法ID失败 -------------------");
        return;
    }

    jstring objectResult = static_cast<jstring>(env->CallStaticObjectMethod(object,
                                                                            staticMethodId));


    const char *string = env->GetStringUTFChars(objectResult, NULL);

    env->DeleteLocalRef(object);
    env ->DeleteLocalRef(objectResult);

    nativeactivity();

    LOGD("从java层返回结果 %s",string);
}


//调用动态注册绑定方法进行绑定
//jint JNI_OnLoad(JavaVM *vm, void *reserved) {
//    JNIEnv *env = NULL;
//    if (vm->GetEnv((void **) &env, JNI_VERSION_1_6) != JNI_OK) {
//        return JNI_ERR;
//    }
//    jint resultNative = registerMethodNativeActivity(env);
//    LOGD("RegisterNatives resultNative: %d", resultNative);
//    return JNI_VERSION_1_6;
//}

jstring rsa_str;

void nativeactivity::nativeMethod() {
    LOGD("native 方法-----------");
}
