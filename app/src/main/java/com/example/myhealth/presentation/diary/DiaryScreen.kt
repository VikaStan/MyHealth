package com.example.myhealth.presentation.diary

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.myhealth.presentation.dashboard.BottomNavBar
import com.example.myhealth.presentation.diary.components.MacroCircleCard
import com.example.myhealth.presentation.diary.components.MealCard
import com.example.myhealth.ui.theme.BackBlue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DiaryScreen(
    navController: NavHostController,
    viewModel: DiaryScreenViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()

    Scaffold(
        containerColor = BackBlue,
        bottomBar = { BottomNavBar(navController) }
    ) { padding ->
        Column(
            Modifier
                .padding(padding)
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                horizontalArrangement = Arrangement.End
            ) {
                IconButton(
                    onClick = { /* open date picker */ },
                    modifier = Modifier
                        .size(40.dp)
                        .background(Color.White, RoundedCornerShape(12.dp))
                ) {
                    Icon(
                        Icons.Default.CalendarMonth,
                        null,
                        tint = com.example.myhealth.ui.theme.PrimaryBlue
                    )
                }
            }

            Spacer(Modifier.size(8.dp))

            MacroCircleCard(
                calories = state.calories,
                target = 2500,
                protein = state.protein,
                fat = state.fat,
                carbs = state.carbs
            )

            Spacer(Modifier.size(16.dp))

            MealCard("завтрак") { /* open AddFoodDialog(MealType.BREAKFAST) */ }
            Spacer(Modifier.size(12.dp))
            MealCard("обед") { /* ... */ }
            Spacer(Modifier.size(12.dp))
            MealCard("ужин") { /* ... */ }

            Spacer(Modifier.size(72.dp))
        }
    }
}