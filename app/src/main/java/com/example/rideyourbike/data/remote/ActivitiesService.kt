package com.example.rideyourbike.data.remote

import com.example.rideyourbike.common.Constants
import com.example.rideyourbike.data.remote.dto.ActivitiesDTO
import retrofit2.http.GET

interface ActivitiesService {

    @GET(Constants.ACTIVITIES_ENDPOINT+"/getLoggedInAthleteActivities")
    suspend fun getAllActivities() : ActivitiesDTO
}