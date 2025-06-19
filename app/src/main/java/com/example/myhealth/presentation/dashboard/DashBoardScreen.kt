package com.example.myhealth.presentation.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.myhealth.ui.theme.BackBlue
import com.example.myhealth.ui.theme.CalRing
import com.example.myhealth.ui.theme.CardBlue
import com.example.myhealth.ui.theme.PrimaryBlue
import com.example.myhealth.ui.theme.WaterRing

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashBoardScreen(
    navController: NavHostController,
    viewModel: DashboardViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()

    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        containerColor = BackBlue,
        topBar = {},
        floatingActionButton = { WaterFab { viewModel.addWater() } },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState) { data ->
                Snackbar(
                    snackbarData = data,
                    containerColor = PrimaryBlue,
                    contentColor = Color.White
                )
            }
        }
    ) { padding ->
        Column(
            Modifier
                .padding(padding)
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState())
        ) {

            // Logo bar
            Box(
                Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
                    .background(CardBlue, RoundedCornerShape(16.dp))
                    .padding(12.dp)
            ) {
                Text("MyHealth", style = MaterialTheme.typography.titleLarge)
            }

            StepsCard(state.steps)

            Spacer(Modifier.height(16.dp))

            // Two circles
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                MetricCircleCard(
                    value = state.waterDrunk,
                    target = 1800,
                    label = "Вода",
                    ringColor = WaterRing,
                    bgColor = Color.White
                )
                MetricCircleCard(
                    value = state.caloriesBurned,
                    target = 2500,
                    label = "Калории",
                    ringColor = CalRing,
                    bgColor = Color.White
                )
            }

            Spacer(Modifier.height(16.dp))

            // Chart block
            Card(
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = CardBlue)
            ) {
                WeeklyBarChart(state.weeklySteps, Modifier
                    .fillMaxWidth()
                    .padding(16.dp))
            }

            Spacer(Modifier.height(72.dp)) // под BottomNav
        }
    }

    // Snackbar при добавлении воды
    LaunchedEffect(state.waterDrunk) {
        snackbarHostState.showSnackbar("+200 мл учтено")
    }
}
