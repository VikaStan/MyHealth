@file:Suppress("DEPRECATION")

package com.example.myhealth.presentation.onboarding

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.myhealth.R
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState


@RequiresApi(Build.VERSION_CODES.Q)
@SuppressLint("ContextCastToActivity")
@Composable
fun OnboardingScreen(
    viewModel: OnboardingViewModel = androidx.hilt.navigation.compose.hiltViewModel(),
    onFinish: () -> Unit
) {
    val pagerState = rememberPagerState()
    val uiState by viewModel.uiState.collectAsState()
    val activity = LocalContext.current as Activity

    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        viewModel.onAuthResult(result.resultCode == Activity.RESULT_OK)
    }

    if (uiState.onboardingComplete) {
        LaunchedEffect(Unit) {
            pagerState.animateScrollToPage(4)
            onFinish()
        }
    }

    Column(
        Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HorizontalPager(
            count = if (uiState.onboardingComplete) 5 else 4,
            state = pagerState,
            modifier = Modifier.weight(1f)
        ) { page ->
            when (page) {
                0 -> Slide(R.drawable.ic_onb_water, R.string.onb_title_1)
                1 -> Slide(R.drawable.ic_onb_fit, R.string.onb_title_2)
                2 -> Slide(R.drawable.ic_onb_bell, R.string.onb_title_3)
                3 -> Slide(R.drawable.ic_onb_done, R.string.onb_title_4)
                4 -> Slide(R.drawable.ic_onb_done, R.string.onb_title_4)
            }
        }

        HorizontalPagerIndicator(
            pagerState = pagerState,
            modifier = Modifier.padding(16.dp),
            activeColor = MaterialTheme.colorScheme.primary
        )

        val lastPage = pagerState.currentPage == 3 && !uiState.onboardingComplete
        Button(
            onClick = { viewModel.onButtonClick(activity, launcher) },
            enabled = lastPage,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(stringResource(id = R.string.onb_button))
        }

        if (uiState.showPermissionRationale) {
            Snackbar {
                Text(stringResource(R.string.permission_denied))
            }
        }
    }
}

@Composable
private fun Slide(imageRes: Int, textRes: Int) {
    Column(
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(painterResource(imageRes), contentDescription = null)
        Spacer(Modifier.height(16.dp))
        Text(stringResource(textRes), style = MaterialTheme.typography.titleLarge)
    }
}
