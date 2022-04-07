package com.project.mvvmtodo.taskdetail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.project.mvvmtodo.R
import com.project.mvvmtodo.databinding.FragmentTaskDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TaskDetailFragment: Fragment(R.layout.fragment_task_detail) {

    private val viewModel: TaskDetailViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentTaskDetailBinding.bind(view)

        binding.apply {
            textViewTaskId.text = viewModel.taskId.toString()
            textViewTaskTitle.text = viewModel.taskTitle
            textViewTaskDescription.text = viewModel.taskDesc
            textViewTaskStatus.text = viewModel.taskStatus.toString()
            textViewTaskCategory.text = viewModel.taskCategory
            textViewTaskDuration.text = "${viewModel.taskDuration.toString()} minutes"
        }
    }
}