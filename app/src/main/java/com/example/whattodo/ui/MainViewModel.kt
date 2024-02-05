package com.example.whattodo.ui

import androidx.lifecycle.ViewModel
import com.example.whattodo.data.entity.ToDo
import com.example.whattodo.repo.ToDoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val toDoRepository: ToDoRepository
) : ViewModel() {
    val getActivity = toDoRepository.getActivity()
    fun changeActivity(toDo: ToDo) = toDoRepository.changeActivity(toDo)
    val getToDo = toDoRepository.getToDo()
    val getFavoriteToDo = toDoRepository.getFavoriteToDo()
}
