pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
    }
    resolutionStrategy {
        eachPlugin {
            if(requested.id.id.toString().startsWith("com.google.cloud.tools.appengine")) {
                useModule("com.google.cloud.tools:appengine-gradle-plugin:${requested.version}")
            }
        }
    }
}

rootProject.name = "FinancialTracker"
include(":androidApp")
include(":shared")
include(":backend")
enableFeaturePreview("VERSION_CATALOGS")
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
