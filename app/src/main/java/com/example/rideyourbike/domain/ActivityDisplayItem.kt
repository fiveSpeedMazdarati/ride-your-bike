package com.example.rideyourbike.domain

data class ActivityDisplayItem(
    val name: String = "",
    val type: String = "",
    val distance: Double = 0.0,
    val averageHeartRate: Int?,
    val maxHeartRate: Int?,
    val hasKudos: Boolean = false
)


