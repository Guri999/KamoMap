import com.android.build.api.dsl.LibraryExtension
import kr.co.build.configurations
import kr.co.build.configureKotlin
import kr.co.build.implementation
import kr.co.build.libs
import kr.co.build.testImplementation
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.kotlin

internal class KamoLibraryPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.library")
                apply("org.jetbrains.kotlin.android")
            }

            extensions.configure<LibraryExtension> {
                configureKotlin(this)
            }

            dependencies {
                configurations(
                    configuration = implementation,
                    libs.timber
                )
                configurations(
                    testImplementation,
                    kotlin("test")
                )
            }
        }
    }
}