// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "7.4.2" apply false
    id("com.android.library") version "7.4.2" apply false
    id("org.jetbrains.kotlin.android") version "1.8.10" apply false
    id("com.google.dagger.hilt.android") version "2.46" apply false
}

buildscript {
    repositories {
        google()
        mavenCentral()
        maven(
            url = java.net.URI(
                "https://jitpack.io"
            )
        )
        maven(
            url = java.net.URI(
                "https://docucdn-a.akamaihd.net/prod/docusignandroidsdk"
            )
        )
    }
}