package ru.ermakov.todo.task

import org.gradle.api.DefaultTask
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.tasks.InputFile
import org.gradle.api.tasks.TaskAction

abstract class HelloNameTask : DefaultTask() {

    @get:InputFile
    abstract val nameFile: RegularFileProperty

    @TaskAction
    fun execute() {
        val name = nameFile.get().asFile.readText()
        println("Hello, $name!")
    }
}