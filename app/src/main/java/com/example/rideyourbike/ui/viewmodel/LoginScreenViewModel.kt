package com.example.rideyourbike.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rideyourbike.ui.main_screen.MainScreenState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LoginScreenViewModel : ViewModel() {

    val _state = MutableStateFlow<MainScreenState>(MainScreenState())
    val state: StateFlow<MainScreenState> = _state

    init {

    }

    fun login () : Boolean {

        return true
    }

    fun getStravaData() {
        viewModelScope.launch {
            // get data from repo
            // update state object
        }
    }

}