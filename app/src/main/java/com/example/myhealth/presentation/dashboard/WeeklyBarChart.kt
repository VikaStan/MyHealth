package com.example.myhealth.presentation.dashboard

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.unit.dp
import com.example.myhealth.ui.theme.PrimaryBlue

@Composable
fun WeeklyBarChart(
    bars: List<Int>,
    modifier: Modifier = Modifier
) {
    Canvas(modifier = modifier.height(140.dp)) {
        val barWidth = size.width / (bars.size * 1.5f)
        val max = (bars.maxOrNull() ?: 1).toFloat()

        bars.forEachIndexed { i, v ->
            val left = i * barWidth * 1.5f
            val top = size.height * (1f - (v / max))

            drawRoundRect(
                color = PrimaryBlue,
                topLeft = Offset(left, top),
                size = Size(barWidth, size.height - top),
                cornerRadius = CornerRadius(6.dp.toPx())
            )
        }
    }
}