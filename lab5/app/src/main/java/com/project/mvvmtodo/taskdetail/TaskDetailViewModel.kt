package com.project.mvvmtodo.taskdetail

import android.util.Log
import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.project.mvvmtodo.api.Task
import com.project.mvvmtodo.api.TaskRepository
import kotlinx.coroutines.launch

class TaskDetailViewModel @ViewModelInject constructor(
    private val repository: TaskRepository,
    @Assisted private val state: SavedStateHandle
): ViewModel() {

    private val _response = MutableLiveData<Task>()
    val responseTask : LiveData<Task>get() = _response

    private val taskId = state.get<String>("id")

    init {
        getTask()
    }

    private fun getTask() = viewModelScope.launch {
        if (taskId != null) {
            repository.getTask(taskId).let { response ->

                if (response.isSuccessful) {
                    _response.postValue(response.body())
                } else {
                    Log.d("UnSuccess", response.code().toString())
                }
            }
        }
    }
}