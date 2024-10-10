import kr.co.build.setNamespace

plugins {
    alias(libs.plugins.kamo.feature)
}

setNamespace("feature.map")

dependencies {
    implementation(libs.kakao.map)
}