plugins {
    id("module-convention")
    alias(libs.plugins.daggerHiltAndroid)
    id("kotlin-kapt")
}

android {
    namespace = "ru.ermakov.feature_settings_api"
    defaultConfig {
        buildConfigField("String", "TOKEN", "\"d\"")
    }

    buildFeatures {
        buildConfig = true
    }
}

dependencies {
    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)
    implementation(projects.core)
}

kapt {
    correctErrorTypes = true
}