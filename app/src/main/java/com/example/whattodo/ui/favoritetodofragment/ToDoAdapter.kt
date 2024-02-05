package com.example.whattodo.ui.favoritetodofragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.whattodo.data.entity.ToDo
import com.example.whattodo.databinding.TodoItemBinding

class ToDoAdapter : ListAdapter<ToDo, ToDoAdapter.ToDoViewHolder>(ToDoDiffUtil()) {

    class ToDoViewHolder(private val viewBinding: TodoItemBinding) :
        RecyclerView.ViewHolder(viewBinding.root) {
        fun bind(item: ToDo?) = with(viewBinding) {
            activityItemTv.text = item?.activity ?: ""
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToDoViewHolder {
        val binding = TodoItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ToDoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ToDoViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}
