package ru.ermakov.todo.task

import org.gradle.api.DefaultTask
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.tasks.OutputFile
import org.gradle.api.tasks.TaskAction

abstract class GenerateNameTask : DefaultTask() {

    @get:OutputFile
    abstract val nameFile: RegularFileProperty

    @TaskAction
    fun execute() {
        val name = "Name #${(0..100).random()}"
        nameFile.asFile.get().writeText(name)
    }
}