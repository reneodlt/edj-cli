import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.7.10"
    id("io.ktor.plugin") version "2.1.3"
    application
    `maven-publish`
}

fun versionString() = nf("TAG_NAME")

fun nf(env: String): String {
    val r = System.getenv(env) ?: ""
    if (r.length > 0)
        return r
    else
        return "SNAPSHOT"
}

group = "com.reneodlt"
version = versionString()


repositories {
    mavenLocal()
    mavenCentral()
    maven("https://maven.pkg.github.com/reneodlt/edj-lib") {
        credentials {
            username = System.getenv("GITHUB_ACTOR")
            password = System.getenv("GITHUB_TOKEN")
        }
    }
}

dependencies {
    implementation("org.apache.commons:commons-configuration2:2.8.0")
    implementation("commons-beanutils:commons-beanutils:1.9.4")

    implementation("com.reneodlt:edj:v0.17.0")
//    implementation("com.reneodlt:edj:SNAPSHOT")

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



publishing {
    publications {
        create<MavenPublication>("maven") {
            pom {

            }
            groupId = "com.reneodlt"
            artifactId = "edj-cli"
            version = versionString()
            from(components["kotlin"])
            artifact(tasks["shadowJar"])
        }
    }

    repositories {
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/reneodlt/edj-cli")
            credentials {
                username = System.getenv("GITHUB_ACTOR")
                password = System.getenv("GITHUB_TOKEN")
            }
        }
//        maven {
//            name = "GitHubPackages"
//            url = uri("https://maven.pkg.github.com/reneodlt/edj-public")
//            credentials {
//                username = System.getenv("GITHUB_ACTOR")
//                password = System.getenv("GITHUB_TOKEN")
//            }
//        }

    }
}
