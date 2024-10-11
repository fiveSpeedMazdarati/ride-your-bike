package com.example.rideyourbike.data.remote

import com.example.rideyourbike.common.Constants
import com.example.rideyourbike.data.remote.dto.ActivitiesDTO
import com.example.rideyourbike.data.remote.dto.ActivitiesDTOItem
import com.example.rideyourbike.data.remote.dto.TokenExchangeResponse
import com.example.rideyourbike.data.remote.dto.TokenExchangeResponseDTO
import retrofit2.http.GET
import retrofit2.http.HeaderMap
import retrofit2.http.POST
import retrofit2.http.Query

interface StravaService {

    @GET(Constants.ACTIVITIES_ENDPOINT)
    suspend fun getAllActivities(@HeaderMap headers: Map<String, String>, @Query("per_page") perPage: Int = 30) : List<ActivitiesDTOItem>

    @POST(Constants.TOKEN_ENDPOINT)
    suspend fun getAccessToken(
                        @Query("client_id") clientID : Int,
                        @Query("client_secret") clientSecret: String,
                        @Query("code") code: String,
                        @Query("grant_type") grantType: String
                ) : TokenExchangeResponse
}