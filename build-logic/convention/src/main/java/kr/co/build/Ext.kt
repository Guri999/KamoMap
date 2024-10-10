package kr.co.build

import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

fun Project.Remote() {
    project.dependencies {
        configurations(
            implementation,
            libs.bundles.ktors
        )
    }
}
