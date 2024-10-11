package com.example.rideyourbike.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rideyourbike.common.Resource
import com.example.rideyourbike.data.remote.dto.ActivitiesDTO
import com.example.rideyourbike.data.remote.dto.ActivitiesDTOItem
import com.example.rideyourbike.data.remote.dto.TokenExchangeResponseDTO
import com.example.rideyourbike.data.repository.TokenExchangeRequestData
import com.example.rideyourbike.domain.use_case.GetAccessTokenUseCase
import com.example.rideyourbike.domain.use_case.GetActivitiesUseCase
import com.example.rideyourbike.presentation.main_screen.MainScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
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

}