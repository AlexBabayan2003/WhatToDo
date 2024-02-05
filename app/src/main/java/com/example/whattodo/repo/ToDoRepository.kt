package com.example.whattodo.repo

import com.example.whattodo.data.entity.ToDo
import kotlinx.coroutines.flow.Flow


interface ToDoRepository {

    fun getActivity(): Flow<ToDo>

    fun getToDo(): Flow<ToDo?>

    fun getFavoriteToDo(): Flow<List<ToDo?>>

    fun changeActivity(toDo: ToDo)
}