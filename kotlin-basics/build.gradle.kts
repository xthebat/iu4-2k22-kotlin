import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.7.10"
    application
}

group = "com.github.xthebat"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    api("com.fasterxml.jackson.core:jackson-core:2.13.4")
    api("com.fasterxml.jackson.core:jackson-annotations:2.13.4")
    api("com.fasterxml.jackson.core:jackson-databind:2.13.4")
    api("com.fasterxml.jackson.module:jackson-module-kotlin:2.13.4")

    api("com.konghq:unirest-java:3.11.12")

    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnit()
}

tasks.withType<KotlinCompile>() {
    kotlinOptions.jvmTarget = "11"
}

application {
    mainClass.set("MainKt")
}