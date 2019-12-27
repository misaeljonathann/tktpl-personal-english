#include <jni.h>
#include <string>

extern "C" JNIEXPORT jstring JNICALL
Java_id_ac_ui_cs_mobileprogramming_misaeljonathan_personalenglish_HomepageFragment_getApiHostFromJNI(
        JNIEnv *env,
        jobject /* this */) {
    std::string apihost = "wordsapiv1.p.rapidapi.com";
    return env->NewStringUTF(apihost.c_str());
}

extern "C" JNIEXPORT jstring JNICALL
Java_id_ac_ui_cs_mobileprogramming_misaeljonathan_personalenglish_HomepageFragment_getApiKeyFromJNI(
        JNIEnv *env,
        jobject /* this */) {
    std::string apikey = "ZWNjYWFmMzE4MW1zaGUxYjQ4MzZhNDBmMDIyNXAxYmI1ZGZqc245YmY0YWVkYWRkNGY=";
    return env->NewStringUTF(apikey.c_str());
}