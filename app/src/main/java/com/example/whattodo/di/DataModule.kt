package com.example.whattodo.di

import android.content.Context
import androidx.room.Room
import com.example.whattodo.data.local.ToDoDao
import com.example.whattodo.data.local.ToDoDb
import com.example.whattodo.data.remote.ToDoApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


@Module
@InstallIn(ViewModelComponent::class)
object DataModule {

    @Provides
    fun provideOkHttpClient(): OkHttpClient =
        OkHttpClient.Builder().addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }).build()

    @Provides
    fun provideRetrofit(client: OkHttpClient): Retrofit =
        Retrofit.Builder().baseUrl("https://www.boredapi.com/api/")
            .addConverterFactory(GsonConverterFactory.create()).client(client).build()

    @Provides
    fun provideApiService(retrofit: Retrofit): ToDoApiService =
        retrofit.create(ToDoApiService::class.java)

    @Provides
    fun provideDatabase(@ApplicationContext context: Context): ToDoDb = Room.databaseBuilder(
        context, ToDoDb::class.java, "todo_db"
    ).fallbackToDestructiveMigration().build()

    @Provides
    fun provideToDoDao(db: ToDoDb): ToDoDao = db.getToDoDao()
}