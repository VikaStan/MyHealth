package com.example.myhealth.presentation.dashboard

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import com.example.myhealth.presentation.WaterLogViewModel
import com.example.myhealth.presentation.statistics.StatsViewModel

@RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
    @Composable
    fun DashBoardScreen(
        modifier: Modifier = Modifier,
        statsViewModel: StatsViewModel = hiltViewModel(),
        waterViewModel: WaterLogViewModel = hiltViewModel()
    ) {
        val stats = statsViewModel.stats.collectAsState()
        val caloriesToday = statsViewModel.caloriesToday.collectAsState(0)

        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            Text(
                text = stringResource(R.string.steps_label, stats.value.steps),
                style = MaterialTheme.typography.bodyLarge
            )
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Card(
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(8.dp),
                    colors = CardDefaults.cardColors()
                ) {
                    Box(Modifier.padding(16.dp)) {
                        Text(
                            text = stringResource(R.string.calories) + ": ${caloriesToday.value}",
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }
                Card(
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(8.dp),
                    colors = CardDefaults.cardColors()
                ) {
                    Box(Modifier.padding(16.dp)) {
                        Text(
                            text = stringResource(
                                R.string.water_label,
                                stats.value.waterDrunk.toInt()
                            ),
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }
            }
            Button(onClick = { waterViewModel.addWater(200f) }) {
                Text(stringResource(R.string.add_water_btn))
            }
        }
    }
