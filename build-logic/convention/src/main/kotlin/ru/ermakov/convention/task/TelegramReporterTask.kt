package ru.ermakov.convention.task

import io.ktor.client.statement.bodyAsText
import kotlinx.coroutines.runBlocking
import org.gradle.api.DefaultTask
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.InputDirectory
import org.gradle.api.tasks.TaskAction
import ru.ermakov.convention.data.remote.TelegramApi
import javax.inject.Inject

abstract class TelegramReporterTask @Inject constructor(
    private val telegramApi: TelegramApi
) : DefaultTask() {

    @get:InputDirectory
    abstract val apkDir: DirectoryProperty

    @get:Input
    abstract val token: Property<String>

    @get:Input
    abstract val chatId: Property<String>

    @TaskAction
    fun report() {
        val token = token.get()
        val chatId = chatId.get()
        apkDir.get().asFile.listFiles()
            ?.filter { it.name.endsWith(".apk") }
            ?.forEach { file ->
                runBlocking {
                    telegramApi.sendMessage(
                        message = "Build finished",
                        token = token,
                        chatId = chatId,
                    ).apply { println(bodyAsText()) }
                }
                runBlocking {
                    telegramApi.upload(file = file, token = token, chatId = chatId).apply {
                        println(bodyAsText())
                    }
                }
            }
    }
}