package com.example.myhealth.presentation.onboarding


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myhealth.domain.usecases.AuthorizeFitUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    private val authUseCase: AuthorizeFitUseCase
) : ViewModel() {

    var uiState by mutableStateOf(GoogleFitAuthState())
        private set

    fun connectGoogleFit() {
        viewModelScope.launch {
            uiState = uiState.copy(inProgress = true)
            val success = authUseCase().isSuccess
            uiState = GoogleFitAuthState(connected = success)
        }
    }
}

