// Top-level build file where you can add configuration options common to all sub-projects/modules.

buildscript {
    dependencies {
        // Replace "22.3.0" with your Kotlin version
        classpath("org.jetbrains.kotlin.plugin.compose:org.jetbrains.kotlin.plugin.compose.gradle.plugin:2.2.21")
    }
}

plugins {
    id("com.android.application") version "8.11.0" apply false
    id("com.android.library") version "8.11.0" apply false
    id("org.jetbrains.kotlin.android") version "2.2.21" apply false
    id("de.mannodermaus.android-junit5") version "2.0.0" apply false

}