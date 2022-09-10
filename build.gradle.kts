plugins {
    alias(libs.plugins.kotlin.gradle.alias)
}

buildscript {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
    dependencies {
//        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.7.10")
        classpath("com.android.tools.build:gradle:7.2.2")
        classpath("com.squareup.sqldelight:gradle-plugin:1.5.0")
        classpath("org.jetbrains.kotlin:kotlin-serialization:1.7.10")
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
    }
}