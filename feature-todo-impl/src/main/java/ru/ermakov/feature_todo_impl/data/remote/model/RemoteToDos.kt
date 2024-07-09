package ru.ermakov.feature_todo_impl.data.remote.model

import com.google.gson.annotations.SerializedName

data class RemoteToDos(
    @SerializedName("list")
    val toDos: List<RemoteToDo>
)