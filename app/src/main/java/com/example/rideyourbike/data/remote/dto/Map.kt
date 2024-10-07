package com.example.rideyourbike.data.remote.dto


import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
data class Map(
    @SerializedName("id")
    val id: String,
    @SerializedName("resource_state")
    val resourceState: Int,
    @SerializedName("summary_polyline")
    val summaryPolyline: String
) : Parcelable