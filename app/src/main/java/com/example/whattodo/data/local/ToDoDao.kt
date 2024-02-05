package com.example.whattodo.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.whattodo.data.entity.ToDo
import kotlinx.coroutines.flow.Flow

@Dao
interface ToDoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertActivity(toDo: ToDo)

    @Update
    fun changeActivity(toDo: ToDo)

    @Query("SELECT * FROM ToDo ORDER BY id DESC LIMIT 1")
    fun getLastToDo(): Flow<ToDo?>

    @Query("SELECT * FROM ToDo WHERE favorite = 1")
    fun getFavoriteToDo(): Flow<List<ToDo?>>

}