package kr.co.build

import org.gradle.kotlin.dsl.DependencyHandlerScope

internal fun DependencyHandlerScope.configurations(configuration: String, vararg impl: Any) {
    impl.forEach {
        add(configuration, it)
    }
}

internal const val implementation = "implementation"

internal const val testImplementation = "testImplementation"

internal const val api = "api"

internal const val ksp = "ksp"

internal const val debugImplementation = "debugImplementation"

internal const val androidTestImplementation = "androidTestImplementation"