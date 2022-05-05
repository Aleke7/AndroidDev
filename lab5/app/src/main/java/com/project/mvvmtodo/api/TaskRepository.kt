package com.project.mvvmtodo.api

import javax.inject.Inject

class TaskRepository @Inject constructor(
    private val apiService: ApiService
) {
    suspend fun getTasks() = apiService.getTasks()
    suspend fun getTask(taskId: String) = apiService.getTask(taskId)
}