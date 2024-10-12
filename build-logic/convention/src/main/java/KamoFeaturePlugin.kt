import kr.co.build.configurations
import kr.co.build.implementation
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

internal class KamoFeaturePlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("kamo.library")
                apply("kamo.hilt")
                apply("kamo.compose")
            }

            dependencies {
                configurations(
                    implementation,
                    project(":core:ui"),
                    project(":core:domain"),
                    project(":core:common"),
                    project(":core:navigation"),
                    project(":core:model")
                )
            }
        }
    }
}