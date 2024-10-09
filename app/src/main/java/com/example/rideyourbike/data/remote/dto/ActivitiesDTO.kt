package com.example.rideyourbike.data.remote.dto

import android.os.Parcelable
import com.example.rideyourbike.domain.model.ActivityDisplayItem
import kotlinx.parcelize.Parcelize

@Parcelize
data class ActivitiesDTO(val items: List<ActivitiesDTOItem>) : Parcelable

