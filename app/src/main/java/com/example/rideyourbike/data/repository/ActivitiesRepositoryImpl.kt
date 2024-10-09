package com.example.rideyourbike.data.repository

import com.example.rideyourbike.common.Resource
import com.example.rideyourbike.data.remote.ActivitiesService
import com.example.rideyourbike.data.remote.dto.ActivitiesDTO
import com.example.rideyourbike.domain.repository.ActivitiesRepository
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject


class ActivitiesRepositoryImpl @Inject constructor(private val service: ActivitiesService) : ActivitiesRepository {

    override suspend fun getAllActivities(authKey: String): Resource<ActivitiesDTO> {
        return try {
            val activities = service.getAllActivities((mapOf ("authorization" to "Bearer $authKey")))
            Resource.Success(activities)
        } catch (e: HttpException) {
            Resource.Error(null, e.localizedMessage ?: "There was a problem getting the activities.")
        } catch (e: IOException) {
            Resource.Error(data = null, message = e.localizedMessage ?: "There was a connection problem.")
        }
    }


}