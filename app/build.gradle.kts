plugins {
    id("app-module-convention")
    alias(libs.plugins.daggerHiltAndroid)
    id("kotlin-kapt")
    id("telegram-reporter-plugin")
}

telegramReporter {
    token.set(providers.environmentVariable("TG_TOKEN"))
    chatId.set(providers.environmentVariable("TG_CHAT"))
}

dependencies {
    implementation(libs.androidx.navigation.compose)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.hilt.android)
    implementation(libs.androidx.hilt.work)
    implementation(libs.androidx.work.runtime)
    kapt(libs.hilt.android.compiler)
    implementation(libs.androidx.hilt.navigation.compose)
    implementation(projects.core)
    implementation(projects.database)
    implementation(projects.network)
    implementation(projects.featureTodoApi)
    implementation(projects.featureTodoImpl)
}

kapt {
    correctErrorTypes = true
}