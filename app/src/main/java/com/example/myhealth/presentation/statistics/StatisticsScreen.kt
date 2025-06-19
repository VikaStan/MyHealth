package com.example.myhealth.presentation.statistics

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.myhealth.presentation.statistics.components.DailyBarChart
import com.example.myhealth.presentation.statistics.components.SegmentedTabRow
import com.example.myhealth.presentation.statistics.components.WeeklyStepsCard
import com.example.myhealth.presentation.statistics.components.WeeklyWaterCard
import com.example.myhealth.ui.theme.BackBlue
import com.example.myhealth.ui.theme.WaterRing

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StatisticsScreen(
    navController: NavHostController,
    viewModel: StatsViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()

    Scaffold(
        containerColor = BackBlue,
        topBar = {}
    ) { padding ->

        /*  ←  ВОТ здесь открываем Column и СРАЗУ ставим фигурную скобку  */
        Column(
            Modifier
                .padding(padding)
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState())
        ) {

            Text("Статистика", style = MaterialTheme.typography.titleLarge)
            Spacer(Modifier.height(8.dp))

            SegmentedTabRow(
                selected = state.currentTab,
                onSelect = viewModel::switchTab,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(16.dp))

            when (state.currentTab) {
                StatsTab.ACTIVITY -> {
                    WeeklyStepsCard(
                        weeklySteps = state.weeklySteps,
                        avgSteps = state.avgSteps
                    )
                    Spacer(Modifier.height(16.dp))
                }

                StatsTab.HYDRATION -> {
                    WeeklyWaterCard(avgWater = state.avgWater)
                    Spacer(Modifier.height(16.dp))
                    DailyBarChart(
                        bars = state.weeklyWater,
                        barColor = WaterRing,
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                Color.White,
                                RoundedCornerShape(16.dp)
                            )
                            .padding(16.dp)
                    )
                }
            }

            Spacer(Modifier.height(72.dp))   // отступ под BottomNav
        }   // ← закрываем Column
    }
}
