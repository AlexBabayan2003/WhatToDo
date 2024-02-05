package com.example.whattodo.data.remote

import retrofit2.http.GET

interface ToDoApiService {

    @GET("activity")
    suspend fun getApi(): ToDoResponse

//    @GET("activity?minprice=:minprice&maxprice=:maxprice")
//    suspend fun getFilteredPriceApi(maxprice): ToDoResponse

}