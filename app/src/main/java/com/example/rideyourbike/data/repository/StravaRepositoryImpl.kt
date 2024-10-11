package com.example.rideyourbike.data.repository

import com.example.rideyourbike.common.Resource
import com.example.rideyourbike.data.remote.StravaService
import com.example.rideyourbike.data.remote.dto.ActivitiesDTO
import com.example.rideyourbike.data.remote.dto.ActivitiesDTOItem
import com.example.rideyourbike.data.remote.dto.TokenExchangeResponse
import com.example.rideyourbike.domain.repository.StravaRepository
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject


class StravaRepositoryImpl @Inject constructor(private val service: StravaService) : StravaRepository {

    override suspend fun getAllActivities(authCode: String): Resource<List<ActivitiesDTOItem>> {
        return try {
            val activities = service.getAllActivities((mapOf ("Authorization" to "Bearer $authCode")))
            Resource.Success(activities)
        } catch (e: HttpException) {
            Resource.Error(null, e.localizedMessage ?: "There was a problem getting the activities.")
        } catch (e: IOException) {
            Resource.Error(data = null, message = e.localizedMessage ?: "There was a connection problem when getting activities.")
        }
    }

    override suspend fun getAccessToken(data: TokenExchangeRequestData): Resource<TokenExchangeResponse> {
        return try {

            val activities = service.getAccessToken(clientID = 136135, clientSecret = data.clientSecret, code = data.code, grantType = data.grantType)
            Resource.Success(activities)
        } catch (e: HttpException) {
            Resource.Error(null, e.localizedMessage ?: "There was a problem getting the access token.")
        } catch (e: IOException) {
            Resource.Error(data = null, message = e.localizedMessage ?: "There was a connection problem when getting the access token.")
        }
    }


}