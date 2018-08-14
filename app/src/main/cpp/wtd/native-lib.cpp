#include <jni.h>
#include <string>
#include "../wtd/Parser/WTDParser.h"
#include "PublicFun.h"
#include "Parser/WTDE26Parser.h"


extern "C"
JNIEXPORT jstring

JNICALL
Java_lxkj_train_com_JNIUtil_getWorld(JNIEnv *env, jobject /* this */) {

    return env->NewStringUTF(wtd::PublicFun::LongToStr(100000).c_str());

}
