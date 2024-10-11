package com.example.rideyourbike.domain.use_case

import com.example.rideyourbike.common.Resource
import com.example.rideyourbike.data.remote.dto.ActivitiesDTO
import com.example.rideyourbike.data.remote.dto.ActivitiesDTOItem
import com.example.rideyourbike.domain.repository.StravaRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetActivitiesUseCase @Inject constructor (
    private val repository : StravaRepository
) {
    suspend fun getActivities(authToken: String): Resource<List<ActivitiesDTOItem>>  {

        val activities = repository.getAllActivities(authToken)

        return if (activities.data != null) {
            Resource.Success(activities.data)
        } else {
            Resource.Error(null, activities.message ?: "No error details available")
        }
    }
}