import kr.co.build.Remote
import kr.co.build.setNamespace

plugins {
    alias(libs.plugins.kamo.library)
    alias(libs.plugins.kamo.hilt)
    alias(libs.plugins.sgp)
    alias(libs.plugins.kotlin.serialization)
}

setNamespace("core.remote")

android {
    buildFeatures {
        buildConfig = true
    }
}

dependencies {
    implementation(projects.core.common)

    Remote()
}