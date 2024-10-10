import com.android.build.api.dsl.LibraryExtension
import kr.co.build.configureHilt
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

internal class HiltPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {

            extensions.configure<LibraryExtension> {
                configureHilt(this)
            }
        }
    }
}