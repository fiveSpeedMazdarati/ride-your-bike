package com.example.rideyourbike.presentation.main_screen

import com.example.rideyourbike.data.remote.dto.ActivitiesDTO

data class MainScreenState(
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val data: ActivitiesDTO? = null,
    val displayLoginButton: Boolean = true
)
