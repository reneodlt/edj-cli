import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.7.10"
    id("io.ktor.plugin") version "2.1.3"
    application
}

group = "com.reneo"
version = "1.0-SNAPSHOT"

repositories {
    mavenLocal()
    mavenCentral()
    maven("https://maven.pkg.github.com/reneodlt/edj-lib") {
        credentials {
            username = "ragmondo"
            password = "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"
        }
    }
}

dependencies {
    implementation("org.apache.commons:commons-configuration2:2.8.0")
    implementation("commons-beanutils:commons-beanutils:1.9.4")

//    implementation("com.reneodlt:edj:v0.13.0")
    implementation("com.reneodlt:edj:SNAPSHOT")

    implementation("org.apache.logging.log4j:log4j:2.19.0")
    implementation("org.slf4j:slf4j-simple:2.0.3")
    implementation("org.jetbrains.kotlinx:kotlinx-cli:0.3.5")

    testImplementation(kotlin("test"))
}

application {
    mainClass.set("MainKt")
}

//ktor {
//    fatJar {
//        archiveFileName.set("edj-cli.jar")
//    }
//}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile>() {
    kotlinOptions.jvmTarget = "15"
}

application {
    mainClass.set("MainKt")
}