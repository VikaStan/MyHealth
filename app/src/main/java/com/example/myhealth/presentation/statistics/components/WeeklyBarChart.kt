package com.example.myhealth.presentation.statistics.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.example.myhealth.ui.theme.PrimaryBlue

@Composable
fun WeeklyBarChart(
    bars: List<Int>,
    modifier: Modifier = Modifier
) {
    // 7 колонок, равномерно по ширине
    val barColor = PrimaryBlue
    val corner = with(LocalDensity.current) { 6.dp.toPx() }
    Canvas(modifier = modifier.height(160.dp)) {
        val barWidth = size.width / (bars.size * 2)
        val max = (bars.maxOrNull() ?: 1).toFloat()

        bars.forEachIndexed { i, v ->
            val left = i * barWidth * 2
            val top = size.height * (1f - (v / max))

            drawRoundRect(
                color = barColor,
                topLeft = Offset(left, top),
                size = Size(barWidth, size.height - top),
                cornerRadius = CornerRadius(corner)
            )
        }

        // подписи дней
        val labels = listOf("Пн", "Вт", "Ср", "Чт", "Пт", "Сб", "Вс")
        labels.forEachIndexed { i, label ->
            drawContext.canvas.nativeCanvas.apply {
                val textPaint = android.graphics.Paint().apply {
                    color = 0xFF6A7AFF.toInt()
                    textSize = 28f
                    textAlign = android.graphics.Paint.Align.CENTER
                }
                drawText(
                    label,
                    (i * barWidth * 2 + barWidth / 2),
                    size.height + 32f,
                    textPaint
                )
            }
        }
    }
}