import kr.co.build.setNamespace

plugins {
    alias(libs.plugins.kamo.library)
}

setNamespace("core.common")

android {
    buildFeatures {
        buildConfig = true
    }
}

dependencies {
    implementation(libs.kotlinx.coroutines.android)
}