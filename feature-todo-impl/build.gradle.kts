plugins {
    id("module-convention")
    alias(libs.plugins.jetbrainsKotlinSerialization)
    alias(libs.plugins.daggerHiltAndroid)
    id("kotlin-kapt")
}

android {
    namespace = "ru.ermakov.feature_todo_impl"
}

dependencies {
    implementation(libs.kotlinx.datetime)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)
    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.androidx.hilt.work)
    implementation(libs.androidx.work.runtime)
    kapt(libs.androidx.hilt.compiler)
    implementation(libs.androidx.room.runtime)
    kapt(libs.androidx.room.compiler)
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(projects.core)
    implementation(projects.database)
    implementation(projects.network)
    implementation(projects.featureTodoApi)
}

kapt {
    correctErrorTypes = true
}