plugins {
    id("base-android-plugin")
    alias(libs.plugins.daggerHiltAndroid)
    id("kotlin-kapt")
}

android {
    namespace = "ru.ermakov.network"

    defaultConfig {
        buildConfigField("String", "TOKEN", providers.environmentVariable("TOKEN"))
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