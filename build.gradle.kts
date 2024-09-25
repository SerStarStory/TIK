import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.8.10"
}

buildscript {
    repositories {
        mavenCentral()
    }
}

group = "ua.serstarstory"
version = "1.0-SNAPSHOT"

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "17"
}

repositories {
    mavenCentral()
    maven(url = "https://repo1.maven.org/maven2")
}

dependencies {
    implementation("org.jetbrains.kotlinx:kandy-lets-plot:0.7.0")
}