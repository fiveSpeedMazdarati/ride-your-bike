package com.example.rideyourbike.domain.use_case

import com.example.rideyourbike.common.Resource
import com.example.rideyourbike.domain.model.ActivityDisplayItem
import com.example.rideyourbike.domain.repository.ActivitiesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetActivitiesUseCase (
    private val repository : ActivitiesRepository
) {
    operator fun invoke(): Flow<Resource<ActivityDisplayItem>> = flow {
        emit(Resource.Loading())

        val activities = repository.getAllActivities()

        activities.data?.let {
            emit(Resource.Success(activities.data))
        }

        if (activities is Resource.Error) {
            emit(Resource.Error(null, activities.message ?: "No error details available"))
        }
    }
}