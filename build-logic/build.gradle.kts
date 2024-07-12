plugins {
    `kotlin-dsl`
}

gradlePlugin {
    plugins.register("my-plugin") {
        id = "my-plugin"
        implementationClass = "ru.ermakov.todo.plugin.MyPlugin"
    }
    plugins.register("telegram-reporter") {
        id = "telegram-reporter"
        implementationClass = "ru.ermakov.todo.telegram.TelegramReporterPlugin"
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
