// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.1.3" apply false
}

buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:8.1.3")
    }
}

// Corrigido: tasks.register fora dos blocos acima, e uso correto de layout
tasks.register<Delete>("clean") {
    delete(layout.buildDirectory)
}