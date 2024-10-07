package com.example.rideyourbike.data.remote.dto


import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
data class Athlete(
    @SerializedName("id")
    val id: Int,
    @SerializedName("resource_state")
    val resourceState: Int
) : Parcelable