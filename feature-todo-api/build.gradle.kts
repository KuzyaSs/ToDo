plugins {
    id("module-convention")
    id("kotlin-kapt")
}

android {
    namespace = "ru.ermakov.feature_todo_api"
}

dependencies {
    implementation(libs.kotlinx.datetime)
    implementation(libs.androidx.room.runtime)
    kapt(libs.androidx.room.compiler)
    implementation(libs.androidx.room.ktx)
    implementation(libs.javax.inject)
    implementation(projects.core)
}

kapt {
    correctErrorTypes = true
}