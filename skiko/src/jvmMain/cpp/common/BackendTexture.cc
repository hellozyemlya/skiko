#include <iostream>
#include <jni.h>
#include "GrBackendSurface.h"


static void deleteBackendTexture(GrBackendTexture* texture) {
  delete texture;
}

extern "C" JNIEXPORT jlong JNICALL Java_org_jetbrains_skia_BackendTextureKt_BackendTexture_1nGetFinalizer
    (JNIEnv* env, jclass jclass) {
  return static_cast<jlong>(reinterpret_cast<uintptr_t>(&deleteBackendTexture));
}

extern "C" JNIEXPORT jboolean JNICALL Java_org_jetbrains_skia_BackendTextureKt__1nIsValid
  (JNIEnv* env, jclass jclass, jlong ptr) {
     GrBackendTexture* texture = reinterpret_cast<GrBackendTexture*>(ptr);
     return texture->isValid();
}

extern "C" JNIEXPORT jint JNICALL Java_org_jetbrains_skia_BackendTextureKt__1nGetBackend
    (JNIEnv* env, jclass jclass, jlong ptr) {
     GrBackendTexture* texture = reinterpret_cast<GrBackendTexture*>(ptr);
     return static_cast<jint>(texture->backend());
}

extern "C" JNIEXPORT jint JNICALL Java_org_jetbrains_skia_BackendTextureKt__1nGetGLTextureId
    (JNIEnv* env, jclass jclass, jlong ptr) {
     GrBackendTexture* texture = reinterpret_cast<GrBackendTexture*>(ptr);
     GrGLTextureInfo glTextureInfo;
     if(texture->getGLTextureInfo(&glTextureInfo))
     return glTextureInfo.fID;
}

extern "C" JNIEXPORT jint JNICALL Java_org_jetbrains_skia_BackendTextureKt__1nGetGLTextureFormat
    (JNIEnv* env, jclass jclass, jlong ptr) {
     GrBackendTexture* texture = reinterpret_cast<GrBackendTexture*>(ptr);
     GrGLTextureInfo glTextureInfo;
     if(texture->getGLTextureInfo(&glTextureInfo))
     return glTextureInfo.fFormat;
}