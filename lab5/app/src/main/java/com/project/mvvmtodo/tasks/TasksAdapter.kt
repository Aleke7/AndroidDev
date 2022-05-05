package com.project.mvvmtodo.tasks

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.project.mvvmtodo.api.Task
import com.project.mvvmtodo.databinding.FragmentTaskBinding

class TasksAdapter(private val listener: OnItemClickListener):
    RecyclerView.Adapter<TasksAdapter.TasksViewHolder>() {

    inner class TasksViewHolder(val binding: FragmentTaskBinding): RecyclerView.ViewHolder(binding.root) {

        init {
            binding.apply {
                root.setOnClickListener {
                    val position = adapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        val task = tasks[position]
                        listener.onItemClick(task.id.toString())
                    }
                }
            }
        }

    }

    interface OnItemClickListener {
        fun onItemClick(taskId: String)
    }

    private val diffCallback = object : DiffUtil.ItemCallback<Task>() {
        override fun areItemsTheSame(oldItem: Task, newItem: Task) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Task, newItem: Task) =
            oldItem == newItem
    }

    private val differ = AsyncListDiffer(this, diffCallback)
    var tasks: List<Task>get() = differ.currentList
        set(value) {
            differ.submitList(value)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TasksViewHolder {
        return TasksViewHolder(FragmentTaskBinding.inflate(
            LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: TasksViewHolder, position: Int) {
        val currentTask = tasks[position]

        holder.binding.apply {
            textViewTitle.text = currentTask.title
            textViewTitle.paint.isStrikeThruText = currentTask.completed
            checkboxCompleted.isChecked = currentTask.completed
        }
    }

    override fun getItemCount() = tasks.size
}