@file:Suppress("DEPRECATION")

package com.example.myhealth.presentation.onboarding

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.myhealth.R
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.delay

@OptIn(ExperimentalPagerApi::class, ExperimentalMaterial3Api::class)
@Composable
fun OnboardingScreen(
    viewModel: OnboardingViewModel = hiltViewModel(),
    onFinished: () -> Unit
) {
    val pages = OnboardingPage.pages
    val pagerState = rememberPagerState()
    val authState = viewModel.uiState
    val snackbarHostState = remember { SnackbarHostState() }

    /* авто-переход после успешного подключения */
    if (authState.connected && pagerState.currentPage == 4) {
        LaunchedEffect(Unit) {
            delay(1_500)
            onFinished()
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("MyHealth") },
                actions = {
                    IconButton(onClick = { /* profile */ }) {
                        Icon(Icons.Default.Person, contentDescription = null)
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
                    Icon(painterResource(R.drawable.ic_fit_logo), null)
                }
            }
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { innerPadding ->

        Box(
            Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {

            /* ---------- Пагер ---------- */
            HorizontalPager(
                count = pages.size,
                state = pagerState,
                userScrollEnabled = !authState.connected,
                modifier = Modifier.fillMaxSize()
            ) { page ->
                OnboardingPageContent(page = pages[page])
            }

            /* ---------- Индикатор ---------- */
            HorizontalPagerIndicator(
                pagerState = pagerState,
                pageCount = pages.size,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(24.dp)
            )
        }

        /* Snackbar после подключения */
        if (authState.connected && pagerState.currentPage == 3) {
            LaunchedEffect(Unit) {
                snackbarHostState.showSnackbar("Подключено!")
            }
        }
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