import kr.co.build.setNamespace

plugins {
    alias(libs.plugins.kamo.feature)
}

setNamespace("feature.main")

dependencies {
    implementation(projects.feature.location)
    implementation(projects.feature.map)
}