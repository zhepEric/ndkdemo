//
// Created by ptlive on 2020/3/25.
//

#include "nativesign.h"

jstring nS(JNIEnv *env,jobject);

jint registerNatives(JNIEnv *pEnv);

JNINativeMethod jniNativeMethod[] = {
        {"getSecretStr","()Ljava/lang/String;",(jstring *) nS}
};

//在此处做APK签名校验，防止不同应用使用so库
jint JNI_OnLoad(JavaVM *vm, void* ex){
    JNIEnv *env = NULL;
    if (vm->GetEnv((void**)&env,JNI_VERSION_1_6) !=JNI_OK){
        return JNI_ERR;
    }
    
    //注册
   jint result = registerNatives(env);

    return JNI_VERSION_1_6;
    
}

jint registerNatives(JNIEnv *pEnv) {

    jclass pJclass = pEnv->FindClass("com/example/piaozhe/ndkdemo/jni/sign/NativeSign");


    return pEnv->RegisterNatives(pJclass,jniNativeMethod, sizeof(jniNativeMethod)/ sizeof(jniNativeMethod[0]));
}

//
jstring nativesign::nativeSecretStr(JNIEnv *env,jobject) {
    
}

//native硬编码RSA公钥，AES密钥
//const char *public_key = "rTdRQmZlc3FusiEW/guBBwuK5yDS1RUBkBOclMASkmGd62GqRYJu/fp4uo/1yDn3";
//使用IDA破解后直接获得此处明文
const char *public_key = "01234";

JNIEXPORT JNICALL
jstring nS(JNIEnv *env,jobject){
    return env->NewStringUTF(public_key);
}