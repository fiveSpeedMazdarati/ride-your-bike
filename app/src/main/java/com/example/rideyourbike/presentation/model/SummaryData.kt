package com.example.rideyourbike.presentation.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SummaryData(
    val countOfActivities: Int,
    val averageDistance: Int,
    val countOfKudos: Int,
    val emoji: String

) : Parcelable
