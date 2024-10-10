import kr.co.build.setNamespace

plugins {
    alias(libs.plugins.kamo.library)
    alias(libs.plugins.kamo.compose)
}

setNamespace("core.ui")

android {
    buildFeatures {
        buildConfig = true
    }
}

dependencies {
    implementation(projects.core.common)
}