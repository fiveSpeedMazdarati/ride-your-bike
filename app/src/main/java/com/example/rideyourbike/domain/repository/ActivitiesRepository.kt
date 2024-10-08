package com.example.rideyourbike.domain.repository

import com.example.rideyourbike.common.Resource
import com.example.rideyourbike.data.remote.dto.ActivitiesDTO
import com.example.rideyourbike.domain.model.ActivityDisplayItem

interface ActivitiesRepository {

    suspend fun getAllActivities(authKey: String) : Resource<ActivitiesDTO>
}