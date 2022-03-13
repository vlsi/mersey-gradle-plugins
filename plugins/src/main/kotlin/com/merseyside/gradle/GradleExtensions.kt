@file:Suppress("UnstableApiUsage")

package com.merseyside.gradle

import org.gradle.api.Project
import org.gradle.api.provider.Provider
import org.gradle.plugin.use.PluginDependency

fun Provider<PluginDependency>.id(): String {
    return get().pluginId
}

inline fun <reified T> Project.findTypedProperty(propertyName: String): T {

    val stringProperty = findProperty(propertyName) as? String

    return stringProperty?.let {
        when (T::class) {
            Boolean::class -> stringProperty.toBoolean()
            Int::class -> stringProperty.toInt()
            Float::class -> stringProperty
            else -> it
        }
    } as? T ?: throw Exception("Property $propertyName not found")
}

fun Project.isLocalDependencies(): Boolean =
    findTypedProperty("build.localDependencies")

fun Project.isLocalAndroidDependencies(): Boolean =
    findTypedProperty("build.localAndroidDependencies")

fun Project.isLocalKotlinExtLibrary(): Boolean =
    findTypedProperty("build.localKotlinExtLibrary")