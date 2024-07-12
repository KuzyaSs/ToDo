plugins {
    id("base-android-plugin")
    alias(libs.plugins.daggerHiltAndroid)
    id("kotlin-kapt")
}

android {
    namespace = "ru.ermakov.core"
}

dependencies {
    implementation(libs.kotlinx.datetime)
    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)
}

kapt {
    correctErrorTypes = true
}