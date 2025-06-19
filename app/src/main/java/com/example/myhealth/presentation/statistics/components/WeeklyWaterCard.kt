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
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.myhealth.ui.theme.CardBlue
import com.example.myhealth.ui.theme.WaterRing

@Composable
fun WeeklyWaterCard(
    avgWater: Int,
    targetPerDay: Int = 2500,
    modifier: Modifier = Modifier
) {
    Card(
        modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    "$avgWater / $targetPerDay мл в день",
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(Modifier.height(12.dp))
                LinearProgressIndicator(
                    progress = avgWater / targetPerDay.toFloat(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(10.dp),
                    color = WaterRing,
                    trackColor = CardBlue
                )
            }
            CircularProgressWithIcon(
                progress = avgWater / targetPerDay.toFloat(),
                progressColor = Color(0xFF41A83E),
                centerText = "${(avgWater * 100f / targetPerDay).toInt()}%"
            )
        }
    }
}