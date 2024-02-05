package com.example.whattodo.data.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
@Parcelize
data class ToDo(
    @SerializedName("activity")
    val activity: String,
    @SerializedName("type")
    val type: String,
    @SerializedName("participants")
    val participants: Int,
    @SerializedName("price")
    val price: Double,
    @SerializedName("link")
    val link: String,
    @SerializedName("favorite")
    var favorite: Boolean,
    @PrimaryKey(autoGenerate = true)
    @SerializedName("id")
    var id: Int = 0,
): Parcelable