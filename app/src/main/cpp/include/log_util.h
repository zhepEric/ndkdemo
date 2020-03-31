
#include "../../../../../../../android-sdk/ndk-bundle/sysroot/usr/include/android/log.h"
#ifndef LOG_TAG
#define LOG_TAG "JniNative"
#define TAGD(...) __android_log_print(ANDROID_LOG_INFO,LOG_TAG,__VA_ARGS__)
#endif