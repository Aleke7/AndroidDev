package com.project.mvvmtodo.taskdetail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.project.mvvmtodo.R
import com.project.mvvmtodo.api.Task
import com.project.mvvmtodo.databinding.FragmentTaskDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TaskDetailFragment: Fragment(R.layout.fragment_task_detail) {

    private val viewModel: TaskDetailViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentTaskDetailBinding.bind(view)

        viewModel.responseTask.observe(viewLifecycleOwner) {task ->
            binding.apply {
                textViewTaskUserId.text = task.userId.toString()
                textViewTaskId.text = task.id.toString()
                textViewTaskTitle.text = task.title
            }
        }
    }
}