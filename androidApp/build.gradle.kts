plugins {
    id("com.android.application")
    kotlin("android")
}

android {
    compileSdk = 31
    defaultConfig {
        applicationId = "com.codingpizza.financialtracker.android"
        minSdk = 23
        targetSdk = 31
        versionCode = 1
        versionName = "1.0"
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }

    buildFeatures {
        compose = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = "11"
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.0.5"
    }

}

dependencies {
    val koinVersion = "3.1.5"
    implementation(project(":shared"))
    implementation("com.google.android.material:material:1.5.0")
    implementation("androidx.appcompat:appcompat:1.4.1")
    // ViewModel
    implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:2.4.1")
    // LiveData
    implementation ("androidx.lifecycle:lifecycle-livedata-ktx:2.4.1")
    implementation ("androidx.lifecycle:lifecycle-runtime-ktx:2.4.1")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.0")
    implementation("androidx.activity:activity-ktx:1.4.0")

    // Compose
    implementation("androidx.compose.ui:ui:1.1.1")
    implementation("androidx.activity:activity-compose:1.4.0")
    implementation("androidx.navigation:navigation-compose:2.5.0-alpha04")
    implementation("com.google.accompanist:accompanist-swiperefresh:0.18.0")

    // Koin
    implementation("io.insert-koin:koin-core:$koinVersion")
    implementation("io.insert-koin:koin-android:$koinVersion")
    implementation("io.insert-koin:koin-androidx-compose:$koinVersion")

    // Tooling support (Previews, etc.)
    implementation("androidx.compose.ui:ui-tooling:1.1.1")
    // Foundation (Border, Background, Box, Image, Scroll, shapes, animations, etc.)
    implementation("androidx.compose.foundation:foundation:1.1.1")
    // Material Design
    implementation("androidx.compose.material:material:1.1.1")
    // Material design icons
    implementation("androidx.compose.material:material-icons-core:1.1.1")
    implementation("androidx.compose.material:material-icons-extended:1.1.1")
    // Integration with observables
    implementation("androidx.compose.runtime:runtime-livedata:1.1.1")

    // UI Tests
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:1.1.1")

    // Test Koin
    testImplementation("io.insert-koin:koin-test:$koinVersion")
}