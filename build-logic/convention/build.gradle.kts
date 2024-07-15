plugins {
    `kotlin-dsl`
}

gradlePlugin {
    plugins.register("telegram-reporter-plugin") {
        id = "telegram-reporter-plugin"
        implementationClass = "ru.ermakov.convention.plugin.TelegramReporterPlugin"
    }
}

dependencies {
    implementation(libs.agp)
    implementation(libs.kotlin.gradle.plugin)
    implementation(libs.kotlin.coroutines.core)
    implementation(libs.ktor.client)
    implementation(libs.ktor.client.okhttp)
    implementation(files(libs.javaClass.superclass.protectionDomain.codeSource.location))
}
