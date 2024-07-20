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

android {
    buildFeatures {
        viewBinding = true
    }
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
    implementation(projects.featureSettingsApi)
    implementation(projects.featureSettingsImpl)
    implementation(projects.featureTodoApi)
    implementation(projects.featureTodoImpl)
    implementation(libs.div)
    implementation(libs.div.core)
    implementation(libs.div.json)
    implementation(libs.glide)
}

kapt {
    correctErrorTypes = true
}