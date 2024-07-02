package ru.ermakov.feature_todo_impl.data.mapper

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atStartOfDayIn
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import ru.ermakov.feature_todo_api.data.local.model.LocalToDo
import ru.ermakov.feature_todo_api.domain.model.ToDo
import ru.ermakov.feature_todo_api.domain.model.ToDoRequest
import java.util.UUID

fun ToDo.toLocalToDo(): LocalToDo {
    this.apply {
        return LocalToDo(
            id = id,
            content = content,
            priority = priority,
            isDone = isDone,
            creationDate = creationDate.toInstant(TimeZone.currentSystemDefault()).epochSeconds,
            modificationDate = modificationDate?.toInstant(TimeZone.currentSystemDefault())?.epochSeconds,
            deadline = deadline?.atStartOfDayIn(TimeZone.currentSystemDefault())?.epochSeconds
        )
    }
}

fun ToDoRequest.toToDo(): ToDo {
    this.apply {
        return ToDo(
            id = UUID.randomUUID().toString(),
            content = content,
            priority = priority,
            isDone = false,
            creationDate = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()),
            modificationDate = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()),
            deadline = deadline
        )
    }
}

fun LocalToDo.toToDo(): ToDo {
    this.apply {
        return ToDo(
            id = id,
            content = content,
            priority = priority,
            isDone = isDone,
            creationDate = Instant.fromEpochSeconds(creationDate)
                .toLocalDateTime(TimeZone.currentSystemDefault()),
            modificationDate = modificationDate?.let {
                Instant.fromEpochSeconds(it).toLocalDateTime(TimeZone.currentSystemDefault())
            },
            deadline = deadline?.let {
                Instant.fromEpochSeconds(it).toLocalDateTime(TimeZone.currentSystemDefault()).date
            }
        )
    }
}