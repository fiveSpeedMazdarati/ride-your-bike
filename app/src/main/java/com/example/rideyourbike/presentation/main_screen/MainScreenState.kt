package com.example.rideyourbike.presentation.main_screen

import com.example.rideyourbike.data.remote.dto.ActivitiesDTO
import com.example.rideyourbike.domain.model.ActivityDisplayItem

data class MainScreenState(
    val isLoading: Boolean = false,
    val isLoggedIn: Boolean = false,
    val data: ActivitiesDTO?
)
