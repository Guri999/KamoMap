import com.android.build.api.dsl.ApplicationExtension
import kr.co.build.androidTestImplementation
import kr.co.build.configurations
import kr.co.build.configureCompose
import kr.co.build.configureHilt
import kr.co.build.configureKotlin
import kr.co.build.groupId
import kr.co.build.implementation
import kr.co.build.libs
import kr.co.build.targetSdk
import kr.co.build.testImplementation
import kr.co.build.versionCode
import kr.co.build.versionName
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

internal class KamoApplicationPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.application")
                apply("org.jetbrains.kotlin.android")
                apply("org.jetbrains.kotlin.plugin.compose")
            }

            extensions.configure<ApplicationExtension> {
                defaultConfig.targetSdk = targetSdk

                namespace = project.groupId

                defaultConfig {
                    applicationId = project.groupId
                    versionCode = project.versionCode
                    versionName = project.versionName

                    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
                }
                configureKotlin(this)
                configureCompose(this)
                configureHilt(this)

                packaging {
                    resources {
                        excludes.add("/META-INF/{AL2.0,LGPL2.1}")
                        excludes.add("META-INF/DEPENDENCIES")
                        excludes.add("META-INF/INDEX.LIST")
                    }
                }

                buildFeatures {
                    buildConfig = true
                }

                dependencies {
                    configurations(
                        implementation,
                        libs.timber
                    )
                    
                    configurations(
                        testImplementation,
                        libs.junit,
                    )

                    configurations(
                        androidTestImplementation,
                        libs.androidx.junit
                    )
                }
            }
        }
    }
}