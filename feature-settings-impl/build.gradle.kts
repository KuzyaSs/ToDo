plugins {
    id("module-convention")
    alias(libs.plugins.jetbrainsKotlinSerialization)
    alias(libs.plugins.daggerHiltAndroid)
    id("kotlin-kapt")
}

android {
    namespace = "ru.ermakov.feature_settings_impl"
    defaultConfig {
        buildConfigField("String", "TOKEN", "\"d\"")
    }

    buildFeatures {
        buildConfig = true
    }
}

dependencies {
    implementation(libs.androidx.navigation.compose)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)
    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.androidx.datastore.preferences)
    implementation(projects.core)
    implementation(projects.database)
    implementation(projects.featureSettingsApi)
}

kapt {
    correctErrorTypes = true
}