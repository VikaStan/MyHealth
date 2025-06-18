package com.example.myhealth.presentation.onboarding

data class GoogleFitAuthState(
    val connected: Boolean = false,
    val inProgress: Boolean = false,
    val error: String? = null
)