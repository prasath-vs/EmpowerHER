// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.google.android.libraries.mapsplatform.secrets.gradle.plugin) apply false
    alias(libs.plugins.google.gms.google.services) apply false
    id("org.jetbrains.kotlin.android") version "1.9.0" apply false
}
