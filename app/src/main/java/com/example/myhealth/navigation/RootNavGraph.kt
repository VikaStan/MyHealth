package com.example.myhealth.navigation

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myhealth.data.datastore.SettingsDataStore
import com.example.myhealth.presentation.bottomnav.BottomNavGraphScreen
import com.example.myhealth.presentation.onboarding.OnboardingScreen
import com.example.myhealth.ui.theme.BackBlue
import kotlinx.coroutines.launch

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun MyHealthApp(settings: SettingsDataStore = SettingsDataStore(LocalContext.current)) {

    /* 1. читаем флаг из DataStore */
    val passed by settings.onboardingPassed.collectAsState(initial = null)

    /* 2. пока DataStore не ответил, рисуем пустой экран */
    if (passed == null) {
        Surface(color = BackBlue) {}
        return
    }
    val navController = rememberNavController()

    Scaffold(
        containerColor = BackBlue
    ) { inner ->

            NavHost(
                navController = navController,
                startDestination = if (passed == true) "bottom_nav" else "onboarding",
                modifier = Modifier.padding(inner)
            ) {

                /* ── Экран-контейнер с нижней навигацией ─────────── */
                composable("bottom_nav") { BottomNavGraphScreen() }

                /* ── Онбординг ───────────────────────────────────── */
                composable("onboarding") {
                    val scope = rememberCoroutineScope()
                    OnboardingScreen(
                        onFinished = {
                            /* 4. После успеха: - сохраняем флаг, - переходим */
                            scope.launch { settings.setOnboardingPassed(true) }
                            navController.navigate("bottom_nav") {
                                popUpTo("onboarding") { inclusive = true }
                            }
                        }
                    )
                }

            }
        }
    }