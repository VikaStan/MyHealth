package com.example.myhealth.presentation.onboarding

import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.Color
import com.example.myhealth.R
import com.example.myhealth.ui.theme.LightBlue

sealed class OnboardingPage(@DrawableRes val iconRes: Int, val bgColor: Color, title: String) {
    data object Hydration : OnboardingPage(
        R.drawable.ic_glass,
        LightBlue,
        "Следите за гидратацией\nи калориями каждый день"
    )

    data object StepsCalories :
        OnboardingPage(R.drawable.ic_avatar, LightBlue, "Импортируем шаги\nиз Google Fit")

    data object Reminders :
        OnboardingPage(R.drawable.ic_bell, LightBlue, "Ненавязчивые напоминания\nкаждые 45 минут")

    data object ConnectFit : OnboardingPage(R.drawable.ic_fit_logo, LightBlue, "")
    data object Success : OnboardingPage(R.drawable.ic_check, LightBlue, "Подключено!")

    companion object {
        val pages = listOf(Hydration, StepsCalories, Reminders, ConnectFit, Success)
    }
}