package com.merseyside.gradle.plugin

import com.merseyside.gradle.LoggerExtension
import com.merseyside.gradle.hasKotlinPlugins
import com.merseyside.gradle.kotlinIds
import com.merseyside.gradle.printLog
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.create
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

class KotlinConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) {

        if (!target.pluginManager.hasKotlinPlugins()) throw ExceptionInInitializerError(
            "No id from (${kotlinIds.joinToString(", ")}) was found!"
        )

        val kotlinConventionPluginExtension = target.extensions.create(
            "kotlinConvention",
            KotlinConventionPluginExtension::class
        )

        target.afterEvaluate {
            configureProject(target, kotlinConventionPluginExtension)
        }
    }

    private fun configureProject(
        target: Project,
        kotlinConventionExtension: KotlinConventionPluginExtension
    ) {
        with(kotlinConventionExtension) {
            printLog("Set ${kotlinConventionExtension.jvmTarget} jvmTarget")
            if (compilerArgs.isNotEmpty()) printLog(
                "Add ${compilerArgs.joinToString(", ")} compiler options"
            )

            target.tasks.withType(KotlinCompile::class) {
                it.kotlinOptions.apply {
                    jvmTarget = this@with.jvmTarget.toString()
                    freeCompilerArgs = compilerArgs
                }
            }
        }
    }
}

open class KotlinConventionPluginExtension : LoggerExtension {
    var jvmTarget: JavaVersion = JavaVersion.VERSION_11
    var compilerArgs: List<String> = emptyList()

    fun setCompilerArgs(vararg args: String) {
        compilerArgs = args.toList()
    }

    override var debug: Boolean = false
    override val TAG: String = "KotlinConventionPlugin"
}