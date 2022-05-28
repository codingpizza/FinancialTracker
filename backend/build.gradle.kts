import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("kotlin-platform-jvm")
    application
    id("com.github.johnrengelman.shadow") version "7.1.2"
    kotlin("plugin.serialization")
}

dependencies {
    val kotlinxSerialization = "1.3.2"
    val coroutinesVersion = "1.6.0"
    val ktorVersion = "1.6.8"
    val logbackVersion = "1.2.11"
    val koinVersion = "3.1.5"
    val sqlDelightVersion = "1.5.3"

    implementation("org.jetbrains.kotlinx:kotlinx-serialization-core:$kotlinxSerialization")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")
    implementation("io.ktor:ktor-server-core:$ktorVersion")
    implementation("io.ktor:ktor-server-netty:$ktorVersion")
    implementation("io.ktor:ktor-serialization:$ktorVersion")
    implementation("io.ktor:ktor-websockets:$ktorVersion")
    implementation("ch.qos.logback:logback-classic:$logbackVersion")
    implementation("io.insert-koin:koin-core:${koinVersion}")
    implementation("io.insert-koin:koin-test:${koinVersion}")
    implementation("io.insert-koin:koin-ktor:${koinVersion}")
    implementation("com.squareup.sqldelight:sqlite-driver:$sqlDelightVersion")
    implementation("com.squareup.sqldelight:jdbc-driver:$sqlDelightVersion")
    implementation("com.zaxxer:HikariCP:5.0.1")
    // MySQL drivers
    implementation("com.h2database:h2:2.1.210")
    implementation("org.mariadb.jdbc:mariadb-java-client:3.0.3")
    implementation("mysql:mysql-connector-java:8.0.28")


    implementation(project(":shared"))
    testImplementation("io.ktor:ktor-server-test-host:$ktorVersion")
    testImplementation("org.jetbrains.kotlin:kotlin-test")
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

tasks.create("stage") {
    dependsOn("installDist")
}

tasks{
    shadowJar {
        manifest {
            attributes(Pair("Main-Class", "com.codingpizza.financialtracker.ServerKt"))
        }
    }
}

application {
    mainClass.set("com.codingpizza.financialtracker.ServerKt")
}