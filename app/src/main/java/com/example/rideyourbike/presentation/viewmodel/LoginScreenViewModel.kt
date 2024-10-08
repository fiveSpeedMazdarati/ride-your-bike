package com.example.rideyourbike.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rideyourbike.common.Resource
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
class LoginScreenViewModel @Inject constructor(private val getActivitiesUseCase: GetActivitiesUseCase) : ViewModel() {

    private val _state = MutableStateFlow(MainScreenState(data = null))
    val state: StateFlow<MainScreenState> = _state.asStateFlow()

    fun getStravaData(authToken: String) {
        viewModelScope.launch {
            getActivitiesUseCase.getActivities(authToken).onEach {
                when(it) {
                    is Resource.Loading -> _state.value = MainScreenState(data = null, isLoading = true, isError = false)
                    is Resource.Error -> _state.value = MainScreenState(data = null, isLoading = false, isError = true)
                    is Resource.Success -> _state.value = MainScreenState(data = it.data, isLoading = false, isError = false)
                }
            }.launchIn(viewModelScope)
        }
    }

}