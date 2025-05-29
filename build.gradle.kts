// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.1.3" apply false
    // Se usar Kotlin:
    kotlin("android") version "1.9.10" apply false
}

buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:8.1.3")
        // Se precisar do classpath do Kotlin (só se usar Kotlin no projeto):
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.9.10")
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
    }
}

tasks.register<Delete>("clean") {
    delete(layout.buildDirectory)
}
