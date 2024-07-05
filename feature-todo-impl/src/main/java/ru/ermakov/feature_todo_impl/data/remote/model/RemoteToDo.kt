package ru.ermakov.feature_todo_impl.data.remote.model

import com.google.gson.annotations.SerializedName

data class RemoteToDo(
    @SerializedName("id")
    val id: String,
    @SerializedName("text")
    val content: String,
    @SerializedName("importance")
    val priority: String,
    @SerializedName("done")
    val isDone: Boolean,
    @SerializedName("created_at")
    val creationDate: Long,
    @SerializedName("changed_at")
    val modificationDate: Long?,
    @SerializedName("deadline")
    val deadline: Long?,
    @SerializedName("last_updated_by")
    val deviceId: String,
)