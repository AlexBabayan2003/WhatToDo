package com.example.whattodo.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.whattodo.data.entity.ToDo

@Database(
    entities = [ToDo::class],
    version = 5
)
abstract class ToDoDb: RoomDatabase() {

    abstract fun getToDoDao(): ToDoDao

}