package com.example.rideyourbike.domain.use_case

import com.example.rideyourbike.common.Resource
import com.example.rideyourbike.data.remote.dto.ActivitiesDTO
import com.example.rideyourbike.domain.repository.ActivitiesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import javax.inject.Inject
import kotlin.coroutines.coroutineContext

class GetActivitiesUseCase @Inject constructor (
    private val repository : ActivitiesRepository
) {
    fun getActivities(authToken: String): Flow<Resource<ActivitiesDTO>> = flow {
        emit(Resource.Loading())

        val activities = repository.getAllActivities(authToken)

        activities.data?.let {
            emit(Resource.Success(activities.data))
        }

        if (activities is Resource.Error) {
            emit(Resource.Error(null, activities.message ?: "No error details available"))
        }
    }
}