package com.project.mvvmtodo.tasks

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.mvvmtodo.api.Task
import com.project.mvvmtodo.api.TaskRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class TasksViewModel @ViewModelInject constructor(
    private val repository: TaskRepository
): ViewModel() {

    private val _response = MutableLiveData<List<Task>>()
    val responseTask : LiveData<List<Task>>get() = _response

    init {
        getTasks()
    }

    private fun getTasks() = viewModelScope.launch {
        repository.getTasks().let { response ->
            if (response.isSuccessful) {
                _response.postValue(response.body())
            } else {
                Log.d("UnSuccessful", response.code().toString())
            }
        }
    }

}