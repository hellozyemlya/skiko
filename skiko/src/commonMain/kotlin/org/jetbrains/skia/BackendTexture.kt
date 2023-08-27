package org.jetbrains.skia

import org.jetbrains.skia.impl.Managed
import org.jetbrains.skia.impl.NativePointer
import org.jetbrains.skia.impl.Stats
import org.jetbrains.skia.impl.reachabilityBarrier

class BackendTexture internal constructor(ptr: NativePointer) : Managed(ptr, _FinalizerHolder.PTR) {
    val isValid: Boolean
        get() = try {
            Stats.onNativeCall()
            _nIsValid(_ptr)
        } finally {
            reachabilityBarrier(this)
        }

    val backend: Int
        get() = try {
            Stats.onNativeCall()
            _nGetBackend(_ptr)
        } finally {
            reachabilityBarrier(this)
        }

    val glTextureId: Int
        get() = try {
            Stats.onNativeCall()
            _nGetGLTextureId(_ptr)
        } finally {
            reachabilityBarrier(this)
        }

    val glTextureFormat: Int
        get() = try {
            Stats.onNativeCall()
            _nGetGLTextureFormat(_ptr)
        } finally {
            reachabilityBarrier(this)
        }

    internal object _FinalizerHolder {
        val PTR = BackendRenderTarget_nGetFinalizer()
    }
}

@ExternalSymbolName("org_jetbrains_skia_BackendTexture__1nGetFinalizer")
private external fun BackendRenderTarget_nGetFinalizer(): NativePointer

@ExternalSymbolName("org_jetbrains_skia_BackendTexture__1nIsValid")
private external fun _nIsValid(ptr: NativePointer): Boolean

@ExternalSymbolName("org_jetbrains_skia_BackendTexture__1nGetBackend")
private external fun _nGetBackend(ptr: NativePointer): Int

@ExternalSymbolName("org_jetbrains_skia_BackendTexture__1nGetGLTextureId")
private external fun _nGetGLTextureId(ptr: NativePointer): Int

@ExternalSymbolName("org_jetbrains_skia_BackendTexture__1nGetGLTextureFormat")
private external fun _nGetGLTextureFormat(ptr: NativePointer): Int