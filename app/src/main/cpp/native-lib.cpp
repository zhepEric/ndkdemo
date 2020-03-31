#include <jni.h>
#include <string>
#include <android/log.h>
#include <android/bitmap.h>

#define TAG "JNI_"
#define LOGD(...) __android_log_print(ANDROID_LOG_DEBUG,TAG,__VA_ARGS__)

jobject generateBitmap(JNIEnv *env, uint32_t newWidth, uint32_t newHeight);

extern "C"
//静态注册
JNIEXPORT jstring

JNICALL
Java_com_example_piaozhe_ndkdemo_MainActivity_stringFromJNI(
        JNIEnv *env,
        jobject /* this */) {
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}




//动态注册
//JNICALL jstring stringFromJNI(JNIEnv *env, jobject /* this */) {
//
//    std::string hello = "Hello from C++";
//    return env->NewStringUTF(hello.c_str());
//}

JNICALL jint localAdd(JNIEnv *env, jobject thiz, jint a, jint b) {
    return a + b;
}

//JNI 调用JAVA层静态方法
JNICALL void native_call_Java(JNIEnv *env, jobject thiz) {
    jclass object = env->FindClass("com/example/piaozhe/ndkdemo/MainActivity");

    if (NULL == object){
        return;
    }
    jmethodID staticMethodId = env->GetStaticMethodID(object, "stringToNative",

                                                      "()Ljava/lang/String;");

    if(NULL == staticMethodId){

    }

    jstring objectResult = (jstring)env->CallStaticObjectMethod(object, staticMethodId);


    const char *string = env->GetStringUTFChars(objectResult, NULL);

    env->DeleteLocalRef(object);
    env ->DeleteLocalRef(objectResult);

    LOGD("从java层返回结果 %s",string);

}

extern "C"
//旋转图片90度
JNIEXPORT jobject
JNICALL
Java_com_example_piaozhe_ndkdemo_MainActivity_setBitmap(JNIEnv *env, jobject, jobject bitmap) {

    AndroidBitmapInfo bitmapInfo;
    int ret;
    LOGD("JNI==============setBitmap");
    if ((ret = AndroidBitmap_getInfo(env, bitmap, &bitmapInfo)) < 0) {
        LOGD("AndroidBitmap_getInfo() FAILED ! ERROR=%d", ret);
        return NULL;
    }
    //读取 bitmap 的像素内容到 native 内存
    void *bitmapPixels;
    if ((ret = AndroidBitmap_lockPixels(env, bitmap, &bitmapPixels)) < 0) {
        LOGD("AndroidBitmap_lockPixels() failed ! error=%d", ret);
        return NULL;
    }

    uint32_t newWidth = bitmapInfo.height;//获取图片高度赋给新的宽度
    uint32_t newHeight = bitmapInfo.width;//
    uint32_t *newBitmapPixels = new uint32_t[newWidth * newHeight];

    int whereToGet = 0;

    for (int x = newWidth; x >= 0; --x) {
        for (int y = 0; y < newHeight; ++y) {
            uint32_t pixel = ((uint32_t *) bitmapPixels)[whereToGet++];
            newBitmapPixels[newWidth * y + x] = pixel;
        }
    }

    jobject newBitmap = generateBitmap(env, newWidth, newHeight);

    void *resultBitmapPixels;

    if ((ret = AndroidBitmap_lockPixels(env, newBitmap, &resultBitmapPixels)) < 0) {
        LOGD("AndroidBitmap_lockPixels() failed ! error=%d", ret);
        return NULL;
    }

    int pixelsCount = newWidth * newHeight;

    memcpy((uint32_t *) resultBitmapPixels, newBitmapPixels, sizeof(uint32_t) * pixelsCount);

    AndroidBitmap_unlockPixels(env, newBitmap);
    delete[] newBitmapPixels;

    return NULL;
}

extern "C"
//灰度图片
JNIEXPORT void
JNICALL Java_com_example_piaozhe_ndkdemo_MainActivity_setWhiteBlackBitmap(JNIEnv *env, jobject obj,
                                                                          jobject jsrcBitmap,
                                                                          jobject desBitmap) {

    AndroidBitmapInfo srcInfo, dstInfo;
    if (ANDROID_BITMAP_RESULT_SUCCESS != AndroidBitmap_getInfo(env, jsrcBitmap, &srcInfo)
        || ANDROID_BITMAP_RESULT_SUCCESS != AndroidBitmap_getInfo(env, desBitmap, &dstInfo)) {
        LOGD("get bitmap info failed");
        return;
    }

    void *srcBuf, *dstBuf;
    if (ANDROID_BITMAP_RESULT_SUCCESS != AndroidBitmap_lockPixels(env, jsrcBitmap, &srcBuf)) {
        LOGD("lock src bitmap failed");
        return;
    }

    if (ANDROID_BITMAP_RESULT_SUCCESS != AndroidBitmap_lockPixels(env, desBitmap, &dstBuf)) {
        LOGD("lock dst bitmap failed");
        return;
    }

    uint32_t w = srcInfo.width;//获取源图片的宽度
    uint32_t h = srcInfo.height;//获取原图片的高度
    int32_t *srcPixs = static_cast<int32_t *>(srcBuf);//
    int32_t *desPixs = static_cast<int32_t *>(dstBuf);

    int alpha = 0xFF << 24;
    int i, j;
    int color;
    int red;
    int green;
    int blue;

    for (i = 0; i < h; ++i) {
        for (j = 0; j < w; ++j) {
            color = srcPixs[w * i + j];

            //分离三原色
            red = ((color & 0x00FF0000) >> 16);
            green = ((color & 0x0000FF00) >> 8);
            blue = color & 0x000000FF;

            //转换为灰度像素
            color = (red + green + blue) / 3;
            color = alpha | (color << 16) | (color << 8) | color;
            desPixs[w * i + j] = color;
        }
    }

    AndroidBitmap_unlockPixels(env, jsrcBitmap);
    AndroidBitmap_unlockPixels(env, desBitmap);
}

//JNIEnv 指代java本地接口环境，
jint registerMethod(JNIEnv *env) {
//找到java层中的类对象，得到jclass
    jclass clz = env->FindClass("com/example/piaozhe/ndkdemo/MainActivity");
    if (clz == NULL) {
        LOGD("can't find class: com/example/piaozhe/ndkdemo/MainActivity");
    }
    JNINativeMethod jniNativeMethod[] = {
//            {"stringFromJNI", "()Ljava/lang/String;",         (void *) stringFromJNI},
            {"addValue", "(II)I", (int *) localAdd}
//            {"nativeCallJava", "()", (void *) native_call_Java}

//                                         {"setBitmap",     "(Landroid/graphics/Bitmap;)Landroid/graphics/Bitmap;", ( *) processBitmap}
    };
    return env->RegisterNatives(clz, jniNativeMethod,
                                sizeof(jniNativeMethod) / sizeof(jniNativeMethod[0]));
}

//动态注册NativeActivity中调用方法
jint registerMethodNativeActivity(JNIEnv *env) {
    jclass clz = env->FindClass("com/example/piaozhe/ndkdemo/NativeActivity");
    if (NULL == clz) {
        LOGD("can't find class: com/example/piaozhe/ndkdemo/NativeActivity");

    }

    JNINativeMethod jniNativeMethod[] = {
            {"nativeCallJava", "()", (void *) native_call_Java}
    };

    return env -> RegisterNatives(clz, jniNativeMethod, sizeof(jniNativeMethod) / sizeof(jniNativeMethod[0]));
}

void createLocalObject(JNIEnv *env) {
    jclass personClass = env->FindClass("com/example/piaozhe/ndkdemo/bean/Person");
    jmethodID pID = env->GetMethodID(personClass, "<init>", "V()");

}

//调用动态注册绑定方法进行绑定
jint JNI_OnLoad(JavaVM *vm, void *reserved) {
    JNIEnv *env = NULL;
    if (vm->GetEnv((void **) &env, JNI_VERSION_1_6) != JNI_OK) {
        return JNI_ERR;
    }
    jint result = registerMethod(env);
//    jint resultNative = registerMethodNativeActivity(env);
//    jint registerAddResult = registerAddMethod(env);
    LOGD("RegisterNatives result: %d", result);
//    LOGD("RegisterNatives resultNative: %d", resultNative);
    return JNI_VERSION_1_6;
}


JNIEXPORT jstring

JNICALL
Java_com_example_piaozhe_ndkdemo_MainActivity_stringJNITest(
        JNIEnv *env,
        jobject /* this */, jstring value) {
//    char buf[128];
//    const jbyte *str;
//    str = reinterpret_cast<const jbyte *>((*env).GetStringUTFChars(value, NULL));
//    if (str == NULL) {
//        return NULL;
//    }
    std::string hello = "native jni";
    const char *strs = (*env).GetStringUTFChars(value, NULL);
    if (strs != NULL) {

        printf("native============%s", strs);

    }
    (*env).ReleaseStringUTFChars(value, strs);
    return env->NewStringUTF(hello.c_str());
}

//重新创建Bitmap对象并返回
jobject generateBitmap(JNIEnv *env, uint32_t newWidth, uint32_t newHeight) {

    jclass bitmapClass = env->FindClass("android/graphics/Bitmap");


    jmethodID pCreatBitmapID = env->GetStaticMethodID(bitmapClass, "createBitmap",
                                                      "(IILandroid/graphics/Bitmap$Config;)Landroid/graphics/Bitmap;");

    jstring configName = env->NewStringUTF("ARGB_8888");
    jclass bitmapConfigClass = env->FindClass("Landroid/graphics/Bitmap$Config;");

    jmethodID valueOfId = env->GetStaticMethodID(bitmapConfigClass, "valueOf",
                                                 "(Ljava/lang/String;)Landroid/graphics/Bitmap$Config;");

    jobject pJConfigobject = env->CallStaticObjectMethod(bitmapConfigClass, valueOfId, configName);

    jobject newBitmap = env->CallStaticObjectMethod(bitmapClass, pCreatBitmapID, newWidth,
                                                    newHeight, pJConfigobject);

    return newBitmap;
}

