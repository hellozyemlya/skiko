
// This file has been auto generated.

#include <string>
#include "SkString.h"
#include "common.h"

static void deleteU16String(std::vector<KChar>* instance) {
    delete instance;
}

SKIKO_EXPORT KNativePointer org_jetbrains_skia_U16String__1nGetFinalizer() {
    return reinterpret_cast<KNativePointer>(&deleteU16String);
}