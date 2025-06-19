@file:Suppress("DEPRECATION")

package com.example.myhealth.presentation.onboarding


import android.content.Context
import android.content.Intent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myhealth.domain.usecases.AuthorizeFitUseCase
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@Suppress("DEPRECATION")
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
    fun getSignInIntent(context: Context): Intent = authUseCase.getSignInIntent(context)

    fun handleSignInResult(data: Intent?) {
        val task = GoogleSignIn.getSignedInAccountFromIntent(data)
        viewModelScope.launch {
            try {
                task.getResult(ApiException::class.java)
                uiState = GoogleFitAuthState(connected = true)
            } catch (e: Exception) {
                uiState = GoogleFitAuthState(error = e.localizedMessage)
            }
        }
    }
}

