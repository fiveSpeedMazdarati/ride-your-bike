package com.example.rideyourbike.domain.model

data class ActivityDisplayItem(
    val name: String = "",
    val type: String = "",
    val distance: Double = 0.0,
    val averageHeartRate: Int = 0,
    val maxHeartRate: Int = 0,
    val hasKudos: Boolean = false
)


