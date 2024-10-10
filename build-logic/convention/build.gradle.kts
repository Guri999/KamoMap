import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `kotlin-dsl`
}

group = "kr.co.buildlogic"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

tasks.withType<KotlinCompile>().configureEach {
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
}

dependencies {
    implementation(files(libs.javaClass.superclass.protectionDomain.codeSource.location))
    implementation(libs.android.gradlePlugin)
    implementation(libs.android.tools.common)
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.ksp.gradlePlugin)
}

tasks {
    validatePlugins {
        enableStricterValidation = true
        failOnWarning = true
    }
}

gradlePlugin {
    plugins {
        register("KamoApplication") {
            id = "kamo.application"
            implementationClass = "KamoApplicationPlugin"
        }
        register("KamoLibrary") {
            id = "kamo.library"
            implementationClass = "KamoLibraryPlugin"
        }
        register("KamoCompose") {
            id = "kamo.compose"
            implementationClass = "KamoComposePlugin"
        }
        register("Hilt") {
            id = "kamo.hilt"
            implementationClass = "HiltPlugin"
        }
        register("KamoFeature") {
            id = "kamo.feature"
            implementationClass = "KamoFeaturePlugin"
        }
    }
}