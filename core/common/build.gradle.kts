import kr.co.build.setNamespace

plugins {
    alias(libs.plugins.kamo.library)
    alias(libs.plugins.kamo.hilt)
}

setNamespace("core.common")