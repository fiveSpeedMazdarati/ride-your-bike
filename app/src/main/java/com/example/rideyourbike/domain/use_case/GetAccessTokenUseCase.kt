package com.example.rideyourbike.domain.use_case

import com.example.rideyourbike.common.Resource
import com.example.rideyourbike.data.remote.dto.TokenExchangeResponse
import com.example.rideyourbike.data.remote.dto.TokenExchangeResponseDTO
import com.example.rideyourbike.data.repository.TokenExchangeRequestData
import com.example.rideyourbike.domain.repository.StravaRepository
import javax.inject.Inject

class GetAccessTokenUseCase @Inject constructor (
    private val repository : StravaRepository
) {
    suspend fun getAccessToken(data: TokenExchangeRequestData): Resource<TokenExchangeResponse> {

        val response = repository.getAccessToken(data)

        return if (response.data != null) {
            Resource.Success(data = response.data)
        } else {
            (Resource.Error(null, response.message ?: "No error details available"))
        }
    }
}