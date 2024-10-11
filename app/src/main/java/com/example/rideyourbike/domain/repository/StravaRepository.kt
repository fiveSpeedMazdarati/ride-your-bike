package com.example.rideyourbike.domain.repository

import com.example.rideyourbike.common.Resource
import com.example.rideyourbike.data.remote.dto.ActivitiesDTO
import com.example.rideyourbike.data.remote.dto.ActivitiesDTOItem
import com.example.rideyourbike.data.remote.dto.TokenExchangeResponse
import com.example.rideyourbike.data.repository.TokenExchangeRequestData

interface StravaRepository {

    suspend fun getAllActivities(authCode: String) : Resource<List<ActivitiesDTOItem>>

    suspend fun getAccessToken(data: TokenExchangeRequestData) : Resource<TokenExchangeResponse>
}