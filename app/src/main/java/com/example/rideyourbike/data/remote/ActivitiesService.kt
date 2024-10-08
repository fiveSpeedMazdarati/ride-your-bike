package com.example.rideyourbike.data.remote

import com.example.rideyourbike.common.Constants
import com.example.rideyourbike.data.remote.dto.ActivitiesDTO
import retrofit2.http.GET
import retrofit2.http.HeaderMap

interface ActivitiesService {

    @GET(Constants.ACTIVITIES_ENDPOINT)
    suspend fun getAllActivities(@HeaderMap headers: Map<String, String>) : ActivitiesDTO
}