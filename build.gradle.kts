import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.7.10"
    application
}

group = "com.reneo"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://maven.pkg.github.com/reneodlt/edj-lib") {
        credentials {
            username = "ragmondo"
            password = "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"
        }
    }
}

dependencies {
    testImplementation(kotlin("test"))
    implementation("com.reneo:edj-lib:1.0-SNAPSHOT")
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile>() {
    kotlinOptions.jvmTarget = "15"
}

application {
    mainClass.set("MainKt")
}