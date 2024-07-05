package ru.ermakov.feature_todo_impl.data.remote.api

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import ru.ermakov.feature_todo_impl.data.remote.model.RemoteToDo
import ru.ermakov.feature_todo_impl.data.remote.model.RemoteToDoResponse
import ru.ermakov.feature_todo_impl.data.remote.model.RemoteToDos
import ru.ermakov.feature_todo_impl.data.remote.model.RemoteToDosResponse

private const val REVISION_HEADER = "X-Last-Known-Revision"

interface ToDoApi {
    @GET("list")
    suspend fun getToDos(): Response<RemoteToDosResponse>

    @GET("list/{toDoId}")
    suspend fun getToDoById(@Path("toDoId") toDoId: String): Response<RemoteToDoResponse>

    @POST("list")
    suspend fun insertToDo(
        @Body toDo: RemoteToDo,
        @Header(REVISION_HEADER) revision: Long
    ): Response<Unit>

    @PATCH("list")
    suspend fun updateToDos(
        @Body toDos: RemoteToDos,
        @Header(REVISION_HEADER) revision: Long
    ): Response<Unit>

    @PUT("list/{toDoId}")
    suspend fun updateToDo(
        @Body toDo: RemoteToDo,
        @Path("toDoId") toDoId: String,
        @Header(REVISION_HEADER) revision: Long
    ): Response<Unit>

    @DELETE("list/{toDoId}")
    suspend fun deleteToDoById(
        @Path("toDoId") toDoId: String,
        @Header(REVISION_HEADER) revision: Long
    ): Response<Unit>
}