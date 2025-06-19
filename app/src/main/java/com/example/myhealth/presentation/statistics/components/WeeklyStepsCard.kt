package com.example.myhealth.presentation.statistics.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.myhealth.R
import com.example.myhealth.ui.theme.PrimaryBlue

@Composable
fun WeeklyStepsCard(
    weeklySteps: List<Int>,
    avgSteps: Int,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    "${weeklySteps.sum()} шагов за неделю",
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(Modifier.height(12.dp))
                DailyBarChart(bars = weeklySteps, barColor = PrimaryBlue)
                Spacer(Modifier.height(12.dp))
                Text("$avgSteps шагов в среднем", style = MaterialTheme.typography.bodyMedium)
            }
            CircularProgressWithIcon(
                progress = (avgSteps / 7000f).coerceIn(0f, 1f),
                iconRes = R.drawable.ic_fit_logo
            )
        }
    }
}