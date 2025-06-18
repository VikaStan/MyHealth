package com.example.myhealth.presentation.onboarding

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.delay

@Composable
fun OnboardingScreen(
    viewModel: OnboardingViewModel = hiltViewModel(),
    onFinished: () -> Unit
) {
    val pages = OnboardingPage.pages
    val pagerState = rememberPagerState()
    val authState = viewModel.uiState

    val systemUiController = rememberSystemUiController()
    val darkIcons = !androidx.compose.foundation.isSystemInDarkTheme()
    LaunchedEffect(darkIcons) {
        systemUiController.setStatusBarColor(Color.Transparent, darkIcons)
    }
    if (authState.connected && pagerState.currentPage == 4) {
        LaunchedEffect(Unit) {
            delay(1500)
            onFinished()
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("MyHealth") },
                actions = {
                    IconButton(onClick = { }) {
                        Icon(Icons.Default.Person, null, tint = MaterialTheme.colorScheme.onPrimary)
                    }
                }
            )
        },
        floatingActionButton = {
            if (pagerState.currentPage == 3 && !authState.connected) {
                FloatingActionButton(
                    onClick = viewModel::connectGoogleFit,
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = Color.White
                ) {
                    Icon(painterResource(R.drawable.ic_water_drop), null)
                }
            }
        },
        snackbarHost = {
            if (authState.connected && pagerState.currentPage == 3) {
                LaunchedEffect(Unit) {
                    (it as SnackbarHostState).showSnackbar("Подключено!")
                }
            }
        }
    ) { inner ->

        HorizontalPager(
            count = pages.size,
            state = pagerState,
            modifier = Modifier
                .padding(inner)
                .fillMaxSize(),
            userScrollEnabled = !authState.connected
        ) { page ->
            OnboardingPageContent(page = pages[page])
        }

        HorizontalPagerIndicator(
            pagerState,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(24.dp)
        )
    }
}

@Composable
private fun OnboardingPageContent(page: OnboardingPage) {
    Column(
        Modifier
            .fillMaxSize()
            .background(page.bgColor.copy(alpha = 0.25f)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            painterResource(page.iconRes),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(120.dp)
        )
        Spacer(Modifier.height(32.dp))
    }
}
