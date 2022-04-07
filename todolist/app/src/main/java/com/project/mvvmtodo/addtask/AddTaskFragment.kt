package com.project.mvvmtodo.addtask

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.project.mvvmtodo.R
import com.project.mvvmtodo.databinding.FragmentAddTaskBinding
import com.project.mvvmtodo.exhaustive
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class AddTaskFragment: Fragment(R.layout.fragment_add_task) {

    private val viewModel: AddTaskViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentAddTaskBinding.bind(view)

        binding.apply {
            editTextTaskName.addTextChangedListener {
                viewModel.taskTitle = it.toString()
            }

            radioGroup.setOnCheckedChangeListener { _, checkedId ->
                if (checkedId == R.id.radio_button_urgent) {
                    viewModel.taskCategoryId = 1
                    viewModel.taskCategoryTitle = "Urgent"
                }
                if (checkedId == R.id.radio_button_important) {
                    viewModel.taskCategoryId = 2
                    viewModel.taskCategoryTitle = "Important"
                }
                if (checkedId == R.id.radio_button_urgent_important) {
                    viewModel.taskCategoryId = 3
                    viewModel.taskCategoryTitle = "Urgent Important"
                }

            }

            fabSaveTask.setOnClickListener {
                viewModel.onSaveClick()
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.addTaskEvent.collect { event ->
                when (event) {
                    is AddTaskViewModel.AddTaskEvent.NavigateBackWithResult -> {
                        binding.editTextTaskName.clearFocus()
                        setFragmentResult(
                            "add_request",
                            bundleOf("add_result" to event.result)
                        )
                        findNavController().popBackStack()
                    }
                    is AddTaskViewModel.AddTaskEvent.ShowInvalidInputMessage -> {
                        Snackbar.make(requireView(), event.msg, Snackbar.LENGTH_LONG).show()
                    }
                }.exhaustive
            }
        }

    }
}