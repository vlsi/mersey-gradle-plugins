enableFeaturePreview("VERSION_CATALOGS")

includeBuild("build-logic")
includeBuild("plugins")

dependencyResolutionManagement {
    repositories {
        mavenCentral()
        mavenLocal()
        google()
    }

    versionCatalogs {
        val group = "io.github.merseyside"
        val catalogVersions = "1.3.3"
        versionCatalogs {
            val androidLibs by creating {
                from("$group:catalog-version-android:$catalogVersions")
            }

            val catalogPlugins by creating {
                from("$group:catalog-version-plugins:$catalogVersions")
            }
        }
    }
}

include(":sample")

rootProject.name = "mersey-gradle-plugins"

