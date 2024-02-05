package com.example.whattodo.repo

import com.example.whattodo.data.local.ToDoDao
import com.example.whattodo.data.remote.ToDoApiService
import com.example.whattodo.data.entity.ToDo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject


class ToDoRepositoryImpl @Inject constructor(
    private val toDoDao: ToDoDao,
    private val toDoApiService: ToDoApiService
) : ToDoRepository {

    override fun getActivity(): Flow<ToDo> = channelFlow {
        val apiResponse = toDoApiService.getApi()
        val toDo = ToDo(
            activity = apiResponse.activity,
            type = apiResponse.type,
            participants = apiResponse.participants,
            price = apiResponse.price,
            link = apiResponse.link,
            favorite = false
        )
        send(toDo)
        toDoDao.insertActivity(toDo)
    }.flowOn(Dispatchers.IO)

    override fun changeActivity(toDo: ToDo) = toDoDao.changeActivity(toDo)

    override fun getToDo(): Flow<ToDo?> = toDoDao.getLastToDo()

    override fun getFavoriteToDo(): Flow<List<ToDo?>> = toDoDao.getFavoriteToDo()
}



