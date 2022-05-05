package com.project.mvvmtodo.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("todos")
    suspend fun getTasks(): Response<List<Task>>

    @GET("todos/{id}")
    suspend fun getTask(@Path("id") taskId: String): Response<Task>

}