// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:2.7.6")
        classpath ("com.google.dagger:hilt-android-gradle-plugin:2.50")
    }
}
    plugins {
        kotlin("kapt") version "1.5.31"
        id("com.android.application") version "8.2.1" apply false
        id("org.jetbrains.kotlin.android") version "1.9.10" apply false
//        id("com.google.dagger.hilt.android") version "2.44" apply false
    }
