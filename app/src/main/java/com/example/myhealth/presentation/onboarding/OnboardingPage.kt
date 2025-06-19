package com.example.myhealth.presentation.onboarding

import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.Color
import com.example.myhealth.R
import com.example.myhealth.ui.theme.LightBlue

data class Page(
    @DrawableRes val iconRes: Int,
    val title: String,
    val bgColor: Color = LightBlue,
)

object OnboardingPage {
    val pages = listOf(
        Page(
            R.drawable.ic_water,
            "Следите за гидратацией\nи калориями каждый день",
        ),
        Page(
            R.drawable.ic_avatar,
            "Импортируем шаги\nиз Google Fit",
        ),
        Page(
            R.drawable.ic_bell,
            "Ненавязчивые напоминания\nкаждые 45 минут",
        ),
        Page(
            R.drawable.ic_fit_logo,
            "",
        ),
        Page(
            R.drawable.ic_check,
            "Подключено!",
        ),
    )
}