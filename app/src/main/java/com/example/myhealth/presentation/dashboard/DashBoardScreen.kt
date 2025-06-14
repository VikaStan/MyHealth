package com.example.myhealth.presentation.dashboard

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.myhealth.R
import com.example.myhealth.presentation.StatsViewModel
import com.example.myhealth.presentation.WaterLogViewModel
import com.example.myhealth.ui.components.ActivityRings

    @RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
    @Composable
    fun DashBoardScreen(
        modifier: Modifier = Modifier,
        statsViewModel: StatsViewModel = hiltViewModel(),
        waterViewModel: WaterLogViewModel = hiltViewModel()
    ) {
        val stats = statsViewModel.stats.collectAsState()

        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            ActivityRings(
                comboProgress = stats.value.steps / 10000f,
                caloriesProgress = 0f,
                sleepProgress = 0f,
                waterProgress = stats.value.water / 2000f,
                componentSize = 150
            )
            Text(
                text = stringResource(R.string.steps_label, stats.value.steps),
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = stringResource(R.string.water_label, stats.value.water.toInt()),
                style = MaterialTheme.typography.bodyLarge
            )
            Button(onClick = { waterViewModel.addWater(200f) }) {
                Text(stringResource(R.string.add_water_btn))
            }
        }
}