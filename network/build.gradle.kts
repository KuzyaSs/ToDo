import java.util.Properties

plugins {
    id("base-android-plugin")
    alias(libs.plugins.daggerHiltAndroid)
    id("kotlin-kapt")
}

android {
    namespace = "ru.ermakov.network"

    defaultConfig {
        val properties = Properties()
        properties.load(project.rootProject.file("local.properties").inputStream())
        buildConfigField("String", "TOKEN", properties.getProperty("TOKEN"))
    }

    buildFeatures {
        buildConfig = true
    }
}

dependencies {
    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(projects.core)
}

kapt {
    correctErrorTypes = true
}