package ru.ermakov.feature_todo_impl.data.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import ru.ermakov.feature_todo_impl.domain.repository.ToDoRepository
import java.util.concurrent.TimeUnit

/**
 * Synchronizes local to-dos with remote ones.
 */
@HiltWorker
class ToDoSyncWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParameters: WorkerParameters,
    private val toDoRepository: ToDoRepository
) : CoroutineWorker(context, workerParameters) {
    override suspend fun doWork(): Result {
        val toDoSyncResult = toDoRepository.syncToDos()
        return when (toDoSyncResult) {
            is ru.ermakov.core.result.Result.Success -> Result.success()
            is ru.ermakov.core.result.Result.Error -> Result.retry()
        }
    }

    companion object {
        private const val WORKER_NAME = "TO_DO_SYNC_WORKER"
        private const val HOUR_PERIOD = 8L

        fun startToDoSyncWorker(context: Context) {
            val constraints = androidx.work.Constraints
                .Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build()
            val toDoSyncWorkerRequest = PeriodicWorkRequestBuilder<ToDoSyncWorker>(
                HOUR_PERIOD,
                TimeUnit.HOURS
            ).setConstraints(constraints).build()
            WorkManager
                .getInstance(context)
                .enqueueUniquePeriodicWork(
                    WORKER_NAME,
                    ExistingPeriodicWorkPolicy.KEEP,
                    toDoSyncWorkerRequest,
                )
        }
    }
}