package kr.co.build

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

internal fun Project.configureKotlin(
    commonExtension: CommonExtension<*, *, *, *, *, *>,
) {
    commonExtension.apply {
        compileSdk = project.compileSdk

        defaultConfig {
            minSdk = project.minSdk
        }

        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_17
            targetCompatibility = JavaVersion.VERSION_17
        }

        tasks.withType<KotlinCompile>().configureEach {
            compilerOptions {
                jvmTarget.set(JvmTarget.JVM_17)
            }
        }
    }
}

internal fun Project.configureCompose(
    commonExtension: CommonExtension<*, *, *, *, *, *>,
) {
    commonExtension.apply {

        buildFeatures {
            compose = true
        }

        composeOptions {
            kotlinCompilerExtensionVersion = libs.versions.composeCompiler.get()
        }

        dependencies {
            configurations(
                configuration = implementation,
                platform(libs.compose.bom),
                libs.bundles.composes,
                libs.bundles.androidx,
                libs.compose.ui.tooling.preview,
            )

            configurations(
                configuration = debugImplementation,
                libs.compose.ui.tooling,
            )
        }
    }
}

internal fun Project.configureHilt(
    commonExtension: CommonExtension<*, *, *, *, *, *>,
) {
    with(pluginManager) {
        apply("com.google.devtools.ksp")
        apply("dagger.hilt.android.plugin")
    }

    commonExtension.apply {
        dependencies {
            configurations(
                configuration = ksp,
                libs.bundles.hilt
            )
            configurations(
                configuration = implementation,
                libs.bundles.hilt
            )
        }
    }
}