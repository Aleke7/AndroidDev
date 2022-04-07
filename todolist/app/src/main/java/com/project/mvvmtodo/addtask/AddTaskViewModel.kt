package com.project.mvvmtodo.addtask

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.material.snackbar.Snackbar
import com.project.mvvmtodo.ADD_TASK_RESULT_OK
import com.project.mvvmtodo.data.Task
import com.project.mvvmtodo.data.TaskDao
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class AddTaskViewModel @ViewModelInject constructor(
    private val taskDao: TaskDao,
    @Assisted private val state: SavedStateHandle
): ViewModel() {

    val task = state.get<Task>("task")

    var taskTitle = state.get<String>("taskTitle") ?: task?.title ?: ""
        set(value) {
            field = value
            state.set("taskTitle", value)
        }
    var taskCategoryTitle = task?.categoryTitle
    var taskCategoryId = task?.categoryId

    private val addTaskEventChannel = Channel<AddTaskEvent>()
    val addTaskEvent = addTaskEventChannel.receiveAsFlow()

    fun onSaveClick() {
        if (taskTitle.isBlank()) {
            showInvalidInputMessage("Name cannot be empty")
            return
        }

        if (task == null) {
            val newTask = taskCategoryId?.let { taskCategoryTitle?.let { it1 -> Task(taskTitle, categoryId = it, categoryTitle = it1) } }
            if (newTask != null) {
                createTask(newTask)
            }
        }
    }

    private fun createTask(task: Task) = viewModelScope.launch {
        taskDao.insert(task)
        addTaskEventChannel.send(AddTaskEvent.NavigateBackWithResult(ADD_TASK_RESULT_OK))
    }

    private fun showInvalidInputMessage(text: String) = viewModelScope.launch {
        addTaskEventChannel.send(AddTaskEvent.ShowInvalidInputMessage(text))
    }

    sealed class AddTaskEvent {
        data class ShowInvalidInputMessage(val msg: String): AddTaskEvent()
        data class NavigateBackWithResult(val result: Int): AddTaskEvent()
    }
}