//
// Created by xushuai on 2019/3/19.
//

#ifndef INCREMENTALUPDATEDEMO_BSUPDATE_H
#define INCREMENTALUPDATEDEMO_BSUPDATE_H

#endif //INCREMENTALUPDATEDEMO_BSUPDATE_H

#include "jni.h"
#include "malloc.h"

int diff(int argc,char* argv[]);
int patch(int argc,char* argv[]);

JNIEXPORT jint JNICALL
Java_com_adair_update_BsUpdateUtils_patchApk(JNIEnv *env, jclass type, jstring oldFilePath_,
                                             jstring newFilePath_, jstring patchPath_);

JNIEXPORT jint JNICALL
Java_com_adair_update_BsUpdateUtils_diffApk(JNIEnv *env, jclass type, jstring oldFilePath_,
                                            jstring newFilePath_, jstring patchPath_);
