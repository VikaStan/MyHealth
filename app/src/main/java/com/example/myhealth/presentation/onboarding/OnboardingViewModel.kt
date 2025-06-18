package com.example.myhealth.presentation.onboarding

import android.app.Activity
import android.content.Intent
import android.os.Build
import androidx.activity.result.ActivityResultLauncher
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myhealth.PreferencesManager
import com.example.myhealth.domain.usecases.AuthorizeFitUseCase
import com.example.myhealth.presentation.util.PermissionHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    private val authUseCase: AuthorizeFitUseCase,
    private val permissionHelper: PermissionHelper,
    private val preferencesManager: PreferencesManager
) : ViewModel() {

    private val _uiState = MutableStateFlow(OnboardingUiState())
    val uiState: StateFlow<OnboardingUiState> = _uiState

    @RequiresApi(Build.VERSION_CODES.Q)
    fun onButtonClick(
        caller: Activity,
        launcher: ActivityResultLauncher<Intent>
    ) = viewModelScope.launch {
        if (permissionHelper.requestActivityRecognition(caller)) {
            val intent = authUseCase.getSignInIntent(caller)
            launcher.launch(intent)
        } else {
            _uiState.value = OnboardingUiState(showPermissionRationale = true)
        }
    }

    fun onAuthResult(success: Boolean) {
        if (success) {
            preferencesManager.setOnboardingComplete()
            _uiState.value = OnboardingUiState(onboardingComplete = true)
        } else {
            _uiState.value = OnboardingUiState(showPermissionRationale = true)
        }
    }
}

data class OnboardingUiState(
    val showPermissionRationale: Boolean = false,
    val onboardingComplete: Boolean = false
)
