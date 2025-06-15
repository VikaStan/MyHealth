package com.example.myhealth.presentation.statistics

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.background
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun StatisticsScreen(viewModel: StatsViewModel = hiltViewModel()) {
    val selectedTab = viewModel.selectedTab

    val stepsData by viewModel.stepsData.collectAsState()
    val caloriesData by viewModel.caloriesData.collectAsState()
    val waterData by viewModel.waterData.collectAsState()
    val proteinsToday by viewModel.proteinsToday.collectAsState()
    val fatsToday by viewModel.fatsToday.collectAsState()
    val carbsToday by viewModel.carbsToday.collectAsState()

    Column(
        Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Статистика",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(bottom = 12.dp)
        )

        // TabRow с тремя вкладками
        TabRow(
            selectedTabIndex = selectedTab.ordinal,
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        ) {
            Tab(selected = selectedTab == StatsTab.ACTIVITY, onClick = { viewModel.selectTab(StatsTab.ACTIVITY) }, text = { Text("Активность") })
            Tab(selected = selectedTab == StatsTab.NUTRITION, onClick = { viewModel.selectTab(StatsTab.NUTRITION) }, text = { Text("Питание") })
            Tab(selected = selectedTab == StatsTab.HYDRATION, onClick = { viewModel.selectTab(StatsTab.HYDRATION) }, text = { Text("Гидратация") })
        }

        Spacer(Modifier.height(24.dp))

        // Содержимое выбранной вкладки
        when (selectedTab) {
            StatsTab.ACTIVITY -> ActivityStatsContent(stepsData)
            StatsTab.NUTRITION -> NutritionStatsContent(
                caloriesData,
                proteinsToday,
                fatsToday,
                carbsToday
            )
            StatsTab.HYDRATION -> HydrationStatsContent(waterData)
        }
    }
}

@Composable
fun HydrationStatsContent(waterData: List<Int>) {
    Card(
        Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFE5F3FF))
    ) {
        Column(Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Text("Вода за неделю", style = MaterialTheme.typography.titleMedium)
            Spacer(Modifier.height(8.dp))
            if (waterData.isEmpty()) {
                Text("Нет данных")
            } else {
                Row {
                    waterData.forEach { ml ->
                        Box(
                            Modifier
                                .height(((ml / 20).coerceAtMost(80)).dp)
                                .width(16.dp)
                                .padding(horizontal = 2.dp)
                                .background(Color(0xFF99C7F3), shape = RoundedCornerShape(4.dp))
                        )
                    }
                }
                Spacer(Modifier.height(12.dp))
                Text("Среднее: ${(waterData.average()).toInt()} мл/день")
            }
        }
    }
}

@Composable
fun NutritionStatsContent(caloriesData: List<Int>, proteins: Int,  fats: Int, carbs: Int) {
    Card(
        Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFC8F2D2))
    ) {
        Column(Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Text("Калорийность за неделю", style = MaterialTheme.typography.titleMedium)
            Spacer(Modifier.height(8.dp))
            if (caloriesData.isEmpty()) {
                Text("Нет данных")
            } else {
                Row {
                    caloriesData.forEach { kcal ->
                        Box(
                            Modifier
                                .height(((kcal / 25).coerceAtMost(80)).dp)
                                .width(16.dp)
                                .padding(horizontal = 2.dp)
                                .background(Color(0xFF70CE93), shape = RoundedCornerShape(4.dp))
                        )
                    }
                }
                Spacer(Modifier.height(12.dp))
                Text("Среднее: ${(caloriesData.average()).toInt()} ккал/день")
            }
            Spacer(Modifier.height(8.dp))
            Text("Б: $proteins г, Ж: $fats г, У: $carbs г (сегодня)")
        }
    }
}

@Composable
fun ActivityStatsContent(stepsData: List<Int>) {
    Card(
        Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFCDE8FF))
    ) {
        Column(Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Text("Шагов за неделю", style = MaterialTheme.typography.titleMedium)
            Spacer(Modifier.height(8.dp))
            if (stepsData.isEmpty()) {
                Text("Нет данных")
            } else {
                Row {
                    stepsData.forEach { steps ->
                        Box(
                            Modifier
                                .height(((steps / 120).coerceAtMost(80)).dp)
                                .width(16.dp)
                                .padding(horizontal = 2.dp)
                                .background(Color(0xFF7FB9FF), shape = RoundedCornerShape(4.dp))
                        )
                    }
                }
                Spacer(Modifier.height(12.dp))
                Text("Среднее: ${(stepsData.average()).toInt()} шагов/день")
            }
        }
    }
}