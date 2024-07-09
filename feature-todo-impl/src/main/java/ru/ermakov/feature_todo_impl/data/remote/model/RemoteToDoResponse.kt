package ru.ermakov.feature_todo_impl.data.remote.model

import com.google.gson.annotations.SerializedName

data class RemoteToDoResponse(
    @SerializedName("element")
    val remoteToDo: RemoteToDo,
    @SerializedName("revision")
    val revision: Long,
)