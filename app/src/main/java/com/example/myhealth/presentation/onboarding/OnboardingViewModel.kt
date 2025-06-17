package com.example.myhealth.presentation.onboarding

import android.app.Activity
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
    private val permissionHelper: PermissionHelper
) : ViewModel() {

    private val _uiState = MutableStateFlow(OnboardingUiState())
    val uiState: StateFlow<OnboardingUiState> = _uiState

    @RequiresApi(Build.VERSION_CODES.Q)
    fun onButtonClick(
        caller: Activity,
        onSuccessNavigate: () -> Unit
    ) = viewModelScope.launch {
        val ctx = permissionHelper.context
        if (permissionHelper.requestActivityRecognition(caller)) {
            val result = authUseCase(ctx)
            if (result.isSuccess) onSuccessNavigate()
        } else {
            _uiState.value = OnboardingUiState(showPermissionRationale = true)
        }
    }
}

data class OnboardingUiState(val showPermissionRationale: Boolean = false)
