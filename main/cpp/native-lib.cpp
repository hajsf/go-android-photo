#include <jni.h>
#include <string>
#include "libfoo.h"

extern "C" JNIEXPORT jstring JNICALL
Java_tk_anative_MainActivity_stringFromJNI(
        JNIEnv* env,
        jobject /* this */) {
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}
extern "C"
void
Java_tk_anative_Global_serverJNI(JNIEnv *env, jobject thiz) {
    server();
}