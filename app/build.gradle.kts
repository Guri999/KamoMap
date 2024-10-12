plugins {
    alias(libs.plugins.kamo.application)
    alias(libs.plugins.sgp)
}

android {

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    packaging {
        resources {
            pickFirsts.add("META-INF/gradle/incremental.annotation.processors")
        }
    }
}

dependencies {
    implementation(projects.core.ui)
    implementation(projects.core.domain)
    implementation(projects.core.data)
    implementation(projects.core.navigation)
    implementation(projects.feature.main)

    implementation(libs.kakao.map)
}