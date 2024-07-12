package ru.ermakov.todo.task

import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.gradle.api.DefaultTask
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction
import javax.inject.Inject

abstract class TaskWithDependencies @Inject constructor(
    private val dataRepository: DataRepository
) : DefaultTask() {
    @get:Input
    abstract val prefix: Property<String>

    @TaskAction
    fun execute() {
        val data = runBlocking { dataRepository.getData() }
        println("${prefix.get()} $data")
    }
}

class DataRepository {
    suspend fun getData(): String {
        delay(1000L)
        return "Random data = ${(0..100).random()}"
    }
}