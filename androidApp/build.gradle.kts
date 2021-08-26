plugins {
    id("com.android.application")
    kotlin("android")
}

android {
    compileSdk = 30
    defaultConfig {
        applicationId = "com.codingpizza.financialtracker.android"
        minSdk = 23
        targetSdk = 30
        versionCode = 1
        versionName = "1.0"
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }

    buildFeatures {
        // Enables Jetpack Compose for this module
        compose = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
        useIR = true
    }

    composeOptions {
        kotlinCompilerVersion = "1.5.10"
        kotlinCompilerExtensionVersion = "1.0.0"
    }

}

dependencies {
    val koinVersion = "3.1.2"
    implementation(project(":shared"))
    implementation("com.google.android.material:material:1.4.0")
    implementation("androidx.appcompat:appcompat:1.3.1")
    implementation("androidx.constraintlayout:constraintlayout:2.0.4")
    // ViewModel
    implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:2.3.1")
    // LiveData
    implementation ("androidx.lifecycle:lifecycle-livedata-ktx:2.3.1")
    implementation ("androidx.lifecycle:lifecycle-runtime-ktx:2.3.1")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.5.0")
    implementation("androidx.activity:activity-ktx:1.3.0")

    // Compose
    implementation("androidx.compose.ui:ui:1.0.0")
    implementation("androidx.activity:activity-compose:1.3.0")
    implementation("androidx.navigation:navigation-compose:2.4.0-alpha05")

    // Koin
    implementation("io.insert-koin:koin-core:$koinVersion")
    implementation("io.insert-koin:koin-android:$koinVersion")
    implementation("io.insert-koin:koin-androidx-compose:$koinVersion")

    // Tooling support (Previews, etc.)
    implementation("androidx.compose.ui:ui-tooling:1.0.0")
    // Foundation (Border, Background, Box, Image, Scroll, shapes, animations, etc.)
    implementation("androidx.compose.foundation:foundation:1.0.0")
    // Material Design
    implementation("androidx.compose.material:material:1.0.0")
    // Material design icons
    implementation("androidx.compose.material:material-icons-core:1.0.0")
    implementation("androidx.compose.material:material-icons-extended:1.0.0")
    // Integration with observables
    implementation("androidx.compose.runtime:runtime-livedata:1.0.0")

    // UI Tests
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:1.0.0")

    // Test Koin
    testImplementation("io.insert-koin:koin-test:$koinVersion")
}