package ru.ermakov.feature_todo_impl.data.remote.model

import com.google.gson.annotations.SerializedName

data class RemoteToDosResponse(
    @SerializedName("list")
    val remoteToDos: List<RemoteToDo>,
    @SerializedName("revision")
    val revision: Long,
)