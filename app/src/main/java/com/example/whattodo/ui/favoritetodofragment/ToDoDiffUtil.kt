package com.example.whattodo.ui.favoritetodofragment

import androidx.recyclerview.widget.DiffUtil
import com.example.whattodo.data.entity.ToDo

class ToDoDiffUtil: DiffUtil.ItemCallback<ToDo>() {
    override fun areItemsTheSame(oldItem: ToDo, newItem: ToDo): Boolean = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: ToDo, newItem: ToDo): Boolean = oldItem == newItem
}