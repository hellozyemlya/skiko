@file:Suppress("NESTED_EXTERNAL_DECLARATION")
package org.jetbrains.skia.paragraph

import org.jetbrains.skia.impl.Library.Companion.staticLoad
import org.jetbrains.skia.Typeface
import org.jetbrains.skia.FontMgr
import org.jetbrains.skia.impl.Stats
import org.jetbrains.skia.impl.reachabilityBarrier
import org.jetbrains.skia.ExternalSymbolName
import kotlin.jvm.JvmStatic

class TypefaceFontProvider : FontMgr(_nMake()) {
    companion object {
        @JvmStatic
        @ExternalSymbolName("org_jetbrains_skia_TypefaceFontProvider__1nMake")
        external fun _nMake(): Long
        @JvmStatic
        @ExternalSymbolName("org_jetbrains_skia_TypefaceFontProvider__1nRegisterTypeface")
        external fun _nRegisterTypeface(ptr: Long, typefacePtr: Long, alias: String?): Long

        init {
            staticLoad()
        }
    }

    fun registerTypeface(typeface: Typeface?, alias: String? = null): TypefaceFontProvider {
        return try {
            Stats.onNativeCall()
            _nRegisterTypeface(
                _ptr,
                getPtr(typeface),
                alias
            )
            this
        } finally {
            reachabilityBarrier(typeface)
        }
    }

    init {
        Stats.onNativeCall()
    }
}