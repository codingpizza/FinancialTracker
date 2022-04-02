import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

plugins {
    kotlin("multiplatform")
    kotlin("native.cocoapods")
    id("com.android.library")
    id("com.squareup.sqldelight")
    kotlin("plugin.serialization")
}

version = "1.0"

kotlin {
    android()

    val iosTarget: (String, KotlinNativeTarget.() -> Unit) -> KotlinNativeTarget =
        if (System.getenv("SDK_NAME")?.startsWith("iphoneos") == true)
            ::iosArm64
        else
            ::iosX64

    iosTarget("ios") {}
    jvm()

    cocoapods {
        summary = "Some description for the Shared Module"
        homepage = "Link to the Shared Module homepage"
        ios.deploymentTarget = "14.1"
        frameworkName = "shared"
        podfile = project.file("../iosApp/Podfile")
    }
    val ktorVersion = "1.6.1"
    val sqlDelightVersion = "1.5.2"
    val slf4jVersion = "1.7.30"
    val kotlinxSerialization = "1.2.1"
    val koinVersion = "3.1.2"

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.5.2")
                implementation("io.ktor:ktor-client-core:$ktorVersion")
                implementation("io.ktor:ktor-client-cio:$ktorVersion")
                implementation("io.ktor:ktor-client-logging:$ktorVersion")
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-core:$kotlinxSerialization")
                implementation("io.ktor:ktor-client-serialization:$ktorVersion")
                implementation("io.ktor:ktor-client-serialization:$ktorVersion")
                implementation("io.insert-koin:koin-core:${koinVersion}")
                implementation("io.insert-koin:koin-test:${koinVersion}")
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }
        val androidMain by getting {
            dependencies {
                implementation("io.ktor:ktor-client-android:$ktorVersion")
                implementation("com.squareup.sqldelight:android-driver:$sqlDelightVersion")
            }
        }
        val androidTest by getting {
            dependencies {
                implementation(kotlin("test-junit"))
                implementation("junit:junit:4.13.2")
            }
        }
        val iosMain by getting {
            dependencies {
                implementation("io.ktor:ktor-client-ios:$ktorVersion")
                implementation("com.squareup.sqldelight:native-driver:$sqlDelightVersion")
            }
        }
        val iosTest by getting

        sourceSets["jvmMain"].dependencies {
            implementation("io.ktor:ktor-client-apache:$ktorVersion")
            implementation("com.squareup.sqldelight:sqlite-driver:$sqlDelightVersion")
            implementation("com.squareup.sqldelight:jdbc-driver:$sqlDelightVersion")
            implementation("com.zaxxer:HikariCP:5.0.0")
            // MySQL drivers
            implementation("com.h2database:h2:1.4.200")
            implementation("org.mariadb.jdbc:mariadb-java-client:2.7.4")
            implementation("mysql:mysql-connector-java:8.0.27")
            implementation("com.google.cloud.sql:mysql-socket-factory-connector-j-8:1.3.4")
            implementation("org.slf4j:slf4j-simple:$slf4jVersion")
            implementation("io.insert-koin:koin-ktor:${koinVersion}")
        }
    }
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions.jvmTarget = "11"
}

sqldelight {
    database("FinancialTrackerDatabase") {
        packageName = "com.codingpizza.financialtracker.db"
        sourceFolders = listOf("sqldelight")
        dialect = "mysql"
    }
}

android {
    compileSdkVersion(31)
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    defaultConfig {
        minSdkVersion(23)
        targetSdkVersion(31)
    }
}