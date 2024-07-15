package ru.ermakov.todo.plugin

import com.android.build.api.artifact.SingleArtifact
import com.android.build.api.variant.AndroidComponentsExtension
import com.android.build.gradle.internal.tasks.factory.dependsOn
import org.gradle.api.Action
import org.gradle.api.GradleException
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.model.ObjectFactory
import org.gradle.api.provider.Property
import org.gradle.kotlin.dsl.create
import org.gradle.kotlin.dsl.newInstance
import org.gradle.kotlin.dsl.register
import ru.ermakov.todo.task.CheckArtifactsTask
import ru.ermakov.todo.task.DataRepository
import ru.ermakov.todo.task.GenerateNameTask
import ru.ermakov.todo.task.HelloNameTask
import ru.ermakov.todo.task.PrintHelloTask
import ru.ermakov.todo.task.PrintUserNameTask
import ru.ermakov.todo.task.TaskWithDependencies
import java.io.File
import javax.inject.Inject

class MyPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        val androidComponents =
            project.extensions.findByType(AndroidComponentsExtension::class.java)
                ?: throw GradleException("android plugin required.")

        androidComponents.onVariants { variant ->
            println("Variant ${variant.name}")
            val artifacts = variant.artifacts.get(SingleArtifact.APK)
            project.tasks.register("checkDirFor${variant.name}", CheckArtifactsTask::class) {
                apkDir.set(artifacts)
            }
        }

        val extension = project.extensions.create("myPlugin", MyPluginExtension::class)
        project.configureDemoTasks()
        project.afterEvaluate {
            project.configureDepsTask(extension)
        }
    }

    private fun Project.configureDemoTasks() {
        val printHelloTask = tasks.register("printHello", PrintHelloTask::class)
        val printUserNameTask = tasks.register("printUserName", PrintUserNameTask::class)
        printUserNameTask.dependsOn(printHelloTask)

        val userNameTask = tasks.register("userName", GenerateNameTask::class) {
            nameFile.set(File("userName.txt"))
        }
        tasks.register("helloUser", HelloNameTask::class) {
            nameFile.set(userNameTask.get().nameFile)
        }
    }

    private fun Project.configureDepsTask(extension: MyPluginExtension) {
        tasks.register<TaskWithDependencies>("depsTask", DataRepository()).apply {
            configure {
                enabled = extension.deps.enable.getOrElse(true)
                prefix.set(extension.deps.prefix)
            }
        }
    }
}

abstract class MyPluginExtension @Inject constructor(objectFactory: ObjectFactory) {
    val deps = objectFactory.newInstance<DepsExtension>()
    fun deps(action: Action<DepsExtension>) {
        action.execute(deps)
    }
}

interface DepsExtension {
    val enable: Property<Boolean>
    val prefix: Property<String>
}