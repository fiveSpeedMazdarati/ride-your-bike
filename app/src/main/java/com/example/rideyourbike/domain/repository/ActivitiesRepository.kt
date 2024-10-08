package com.example.rideyourbike.domain.repository

import com.example.rideyourbike.common.Resource
import com.example.rideyourbike.domain.model.ActivityDisplayItem

interface ActivitiesRepository {

    suspend fun getAllActivities() : Resource<ActivityDisplayItem>
}