package com.example.whattodo.di

import com.example.whattodo.repo.ToDoRepository
import com.example.whattodo.repo.ToDoRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindToDoRepository(
        repo: ToDoRepositoryImpl
    ): ToDoRepository

}