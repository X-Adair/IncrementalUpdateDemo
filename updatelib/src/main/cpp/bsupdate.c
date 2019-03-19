//
// Created by xushuai on 2019/3/19.
//
#include "jni.h"
#include "bsupdate.h"

JNIEXPORT jint JNICALL
Java_com_adair_update_BsUpdateUtils_patchApk(JNIEnv *env, jclass type, jstring oldFilePath_,
                                             jstring newFilePath_, jstring patchPath_) {

    const char* argv[4];
    argv[0] = "bspatch";
    argv[1] = (*env)->GetStringUTFChars(env, oldFilePath_, 0);
    argv[2] = (*env)->GetStringUTFChars(env, newFilePath_, 0);
    argv[3] = (*env)->GetStringUTFChars(env, patchPath_, 0);
    patch(4,argv);
    (*env)->ReleaseStringUTFChars(env, oldFilePath_, argv[1]);
    (*env)->ReleaseStringUTFChars(env, newFilePath_, argv[2]);
    (*env)->ReleaseStringUTFChars(env, patchPath_, argv[3]);
    return 0;
}

JNIEXPORT jint JNICALL
Java_com_adair_update_BsUpdateUtils_diffApk(JNIEnv *env, jclass type, jstring oldFilePath_,
                                            jstring newFilePath_, jstring patchPath_) {
    const char* argv[4];
    argv[0] = "bsdiff";
    argv[1] = (*env)->GetStringUTFChars(env, oldFilePath_, 0);
    argv[2] = (*env)->GetStringUTFChars(env, newFilePath_, 0);
    argv[3] = (*env)->GetStringUTFChars(env, patchPath_, 0);
    diff(4,argv);
    (*env)->ReleaseStringUTFChars(env, oldFilePath_, argv[1]);
    (*env)->ReleaseStringUTFChars(env, newFilePath_, argv[2]);
    (*env)->ReleaseStringUTFChars(env, patchPath_, argv[3]);
    return 0;
}
