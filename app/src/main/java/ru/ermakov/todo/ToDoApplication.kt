package ru.ermakov.todo

import android.app.Application
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import dagger.hilt.android.HiltAndroidApp
import ru.ermakov.feature_todo_impl.data.worker.ToDoSyncWorker
import javax.inject.Inject

@HiltAndroidApp
class ToDoApplication : Application(), Configuration.Provider {
    @Inject
    lateinit var workerFactory: HiltWorkerFactory

    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()

    override fun onCreate() {
        super.onCreate()
        ToDoSyncWorker.startToDoSyncWorker(context = applicationContext)
    }
}