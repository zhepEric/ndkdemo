//
// Created by ptlive on 2020/3/23.
//

#include <jni.h>
#include <android/log.h>
#include <cstring>
#include "log_util.h"


void native_path(JNIEnv *env,jobject ,jstring path);
jint registerMethod(JNIEnv * env);

JNINativeMethod jniNativeMethod[] = {
        {"play","(Ljava/lang/String;)V", (void *)native_path}
};

jint JNI_OnLoad(JavaVM *vm, void *reserved) {

    JNIEnv *env = NULL;
    if (vm ->GetEnv((void **)&env,JNI_VERSION_1_6) != JNI_OK){
        return JNI_ERR;
    }
    
    jint  result = registerMethod(env);
    return JNI_VERSION_1_6;
}

//通过动态注册可以调用java层 kotlin实现的类
jint registerMethod(JNIEnv * env){
    jclass pJclass = env->FindClass("com/example/piaozhe/ndkdemo/jni/JniNative");

    return env->RegisterNatives(pJclass,jniNativeMethod, sizeof(jniNativeMethod)/ sizeof(jniNativeMethod[0]));
}

extern "C" {
//静态注册方式不能调用kotlin类中方法
JNIEXPORT void JNICALL
Java_com_example_piaozhe_ndkdemo_JniNative_play(
        JNIEnv *env,
        jobject /* this */, jstring path) {

    const char *paths = env->GetStringUTFChars(path, NULL);
    TAGD("lll_path=%s",paths);
}



}

JNICALL void native_path(JNIEnv *env,jobject ,jstring path){
    const char *paths = env->GetStringUTFChars(path, NULL);
    TAGD("lll_path=%s",paths); 
}