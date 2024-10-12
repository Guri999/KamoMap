import kr.co.build.setNamespace

plugins {
    alias(libs.plugins.kamo.library)
    alias(libs.plugins.kotlin.serialization)
}

setNamespace("core.navigation")

dependencies {
    implementation(libs.kotlinx.serialization.json)
}