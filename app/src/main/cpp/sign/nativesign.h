//
// Created by ptlive on 2020/3/25.
//

#ifndef NDKDEMO_NATIVESIGN_H
#define NDKDEMO_NATIVESIGN_H

#include <jni.h>
#include <string.h>
#include "log_util.h"

class nativesign {

public:

    jstring nativeSecretStr(JNIEnv *env,jobject);

};

extern "C"{

}

#endif //NDKDEMO_NATIVESIGN_H
