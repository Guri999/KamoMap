package kr.co.build

import com.android.build.api.dsl.LibraryExtension
import org.gradle.accessors.dm.LibrariesForLibs
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.the
import kotlin.math.pow

fun Project.setNamespace(name: String) {
    extensions.configure<LibraryExtension> {
        namespace = "${project.groupId}.$name"
    }
}

val Project.libs get() = the<LibrariesForLibs>()

val Project.minSdk: Int
    get() = intProperty("minSdk")

val Project.targetSdk: Int
    get() = intProperty("targetSdk")

val Project.compileSdk: Int
    get() = intProperty("compileSdk")

val Project.groupId: String
    get() = stringProperty("POM_GROUP_ID")

val Project.versionName: String
    get() = stringProperty("POM_VERSION")

val Project.versionCode: Int
    get() = versionName
        .takeWhile { it.isDigit() || it == '.' }
        .split('.')
        .map { it.toInt() }
        .reversed()
        .sumByIndexed { index, unit ->
            (unit * 10.0.pow(2 * index + 1)).toInt()
        }

private fun Project.intProperty(
    name: String,
    default: () -> Int = { error("unknown property $name") },
): Int = (properties[name] as String?)?.toInt() ?: default()

private fun Project.stringProperty(
    name: String,
    default: () -> String = { error("unknown property $name") },
): String = (properties[name] as String?) ?: default()


private inline fun <T> List<T>.sumByIndexed(selector: (Int, T) -> Int): Int {
    var index = 0
    var sum = 0
    for (element in this) {
        sum += selector(index++, element)
    }

    return sum
}