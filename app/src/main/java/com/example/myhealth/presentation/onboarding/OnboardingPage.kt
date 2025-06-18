package com.example.myhealth.presentation.onboarding

import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.Color
import com.example.myhealth.R
import com.example.myhealth.ui.theme.LightBlue

sealed class OnboardingPage(@DrawableRes val iconRes: Int, val bgColor: Color) {
    data object Hydration : OnboardingPage(R.drawable.ic_glass, LightBlue)
    data object StepsCalories : OnboardingPage(R.drawable.ic_avatar, LightBlue)
    data object Reminders : OnboardingPage(R.drawable.ic_bell, LightBlue)
    data object ConnectFit : OnboardingPage(R.drawable.ic_fit_logo, LightBlue)
    data object Success : OnboardingPage(R.drawable.ic_check, LightBlue)

    companion object {
        val pages = listOf(Hydration, StepsCalories, Reminders, ConnectFit, Success)
    }
}