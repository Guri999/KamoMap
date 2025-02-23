import kr.co.build.setNamespace

plugins {
    alias(libs.plugins.kamo.library)
    alias(libs.plugins.kamo.hilt)
}

setNamespace("core.data")

dependencies {
    implementation(projects.core.domain)
    implementation(projects.core.remote)
    implementation(projects.core.common)
    implementation(projects.core.model)
}