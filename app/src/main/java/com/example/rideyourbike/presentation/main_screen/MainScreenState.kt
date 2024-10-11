package com.example.rideyourbike.presentation.main_screen

import com.example.rideyourbike.data.remote.dto.ActivitiesDTOItem

data class MainScreenState(
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val accessToken: String = "",
    val data: List<ActivitiesDTOItem> = listOf(),
    val displayLoginButton: Boolean = true,
    val isLoggedIn: Boolean = false,
    val fetchedStravaData: Boolean = false
)
