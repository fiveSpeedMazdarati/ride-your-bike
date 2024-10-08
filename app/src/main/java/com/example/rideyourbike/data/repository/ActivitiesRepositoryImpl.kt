package com.example.rideyourbike.data.repository

import com.example.rideyourbike.common.Resource
import com.example.rideyourbike.data.remote.ActivitiesService
import com.example.rideyourbike.domain.model.ActivityDisplayItem
import com.example.rideyourbike.domain.repository.ActivitiesRepository

class ActivitiesRepositoryImpl(service: ActivitiesService) : ActivitiesRepository {
    override suspend fun getAllActivities(): Resource<ActivityDisplayItem> {
        return Resource.Success(data = ActivityDisplayItem())
    }


}