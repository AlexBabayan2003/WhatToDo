package com.example.whattodo.data.remote

import com.example.whattodo.data.entity.ToDo
import com.google.gson.annotations.SerializedName


data class ToDoResponse(
    @SerializedName("accessibility")
    val accessibility: Double,
    @SerializedName("activity")
    val activity: String,
    @SerializedName("link")
    val link: String,
    @SerializedName("participants")
    val participants: Int,
    @SerializedName("price")
    val price: Double,
    @SerializedName("type")
    val type: String
)
