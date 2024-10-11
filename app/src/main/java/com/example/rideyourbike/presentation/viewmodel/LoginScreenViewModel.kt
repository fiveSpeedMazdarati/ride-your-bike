package com.example.rideyourbike.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.rideyourbike.data.remote.dto.ActivitiesDTOItem
import com.example.rideyourbike.data.repository.TokenExchangeRequestData
import com.example.rideyourbike.domain.use_case.GetAccessTokenUseCase
import com.example.rideyourbike.domain.use_case.GetActivitiesUseCase
import com.example.rideyourbike.presentation.main_screen.MainScreenState
import com.example.rideyourbike.presentation.model.SummaryData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class LoginScreenViewModel @Inject constructor(
    private val getActivitiesUseCase: GetActivitiesUseCase,
    private val getAccessTokenUseCase: GetAccessTokenUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(MainScreenState())
    val state: StateFlow<MainScreenState> = _state.asStateFlow()

    suspend fun getAllTheData(data: TokenExchangeRequestData) {

        _state.value = _state.value.copy(isLoading = true, displayLoginButton = false)

        val token = getAccessToken(data)

        Log.d("LOGIN", "Token: $token")

        val stravaData = getStravaData(token)

        Log.d("LOGIN", "Strava Data: $stravaData")

        _state.value = _state.value.copy(
            isLoading = false,
            isError = false,
            data = stravaData,
            fetchedStravaData = true
        )

    }

    private suspend fun getAccessToken(data: TokenExchangeRequestData): String {

        val fetchedData = getAccessTokenUseCase.getAccessToken(data)
        Log.d("LOGIN", "data from access token exchange: ${fetchedData.data}")
        return fetchedData.data?.accessToken ?: ""

    }


    private suspend fun getStravaData(accessToken: String): List<ActivitiesDTOItem> {
        val fetchedData = getActivitiesUseCase.getActivities(accessToken)

        return fetchedData.data ?: listOf()
    }

    fun setLoggedIn(b: Boolean) {
        _state.value = _state.value.copy(isLoggedIn = b, displayLoginButton = false)
    }

    fun calculateSummaryData(data: List<ActivitiesDTOItem>): SummaryData {
        val distances = data.map { it.distance }
        val totalKudos = data.map { it.hasKudoed }.count {
            it == true
        }

        val emoji = determineEmoji(totalKudos.toFloat(), data.size.toFloat() )

        return SummaryData(
            countOfActivities = data.size,
            averageDistance = distances.average().toInt(),
            countOfKudos = totalKudos,
            emoji = emoji)
    }

    private fun determineEmoji(countOfKudos: Float, countOfActivities: Float) : String {
        val calculatedKudosValue = countOfKudos / countOfActivities

        return when {
            calculatedKudosValue <= .25 -> { "\uD83D\uDE15" }
            calculatedKudosValue <= .75 -> { "\uD83D\uDE03" }
            calculatedKudosValue > .75 -> { "\uD83D\uDD25" }
            else -> ""
        }
    }
}