package com.example.myhealth.presentation.statistics.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun DailyBarChart(
    bars: List<Int>,
    barColor: Color,
    modifier: Modifier = Modifier
) {
    Canvas(modifier.height(100.dp)) {
        val barWidth = size.width / (bars.size * 1.5f)
        val max = (bars.maxOrNull() ?: 1).toFloat()
        val labels = listOf("Пн", "Вт", "Ср", "Чт", "Пт", "Сб", "Вс")
        bars.forEachIndexed { i, v ->
            val left = i * barWidth * 1.5f
            val top = size.height * 0.7f * (1f - v / max)

            drawRoundRect(
                color = barColor,
                topLeft = Offset(left, top),
                size = Size(barWidth, size.height * 0.7f - top),
                cornerRadius = CornerRadius(4.dp.toPx())
            )

            drawContext.canvas.nativeCanvas.drawText(
                labels[i],
                left + barWidth / 2,
                size.height - 4.dp.toPx(),
                android.graphics.Paint().apply {
                    color = barColor.hashCode()
                    textAlign = android.graphics.Paint.Align.CENTER
                    textSize = 12.sp.toPx()
                }
            )
        }
    }
}