package com.example.myhealth.presentation.statistics.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun CircularProgressWithIcon(
    progress: Float,
    trackColor: Color = Color(0xFFE0E5FF),
    iconRes: Int,
    centerText: String? = null,
    modifier: Modifier = Modifier.size(120.dp)
) {
    Box(contentAlignment = Alignment.Center, modifier = modifier) {
        Canvas(modifier = modifier) {
            val stroke = Stroke(width = 14.dp.toPx(), cap = StrokeCap.Round)
            drawArc(
                color = trackColor,
                startAngle = -90f,
                sweepAngle = 360f,
                useCenter = false,
                style = stroke
            )
            drawArc(
                color = trackColor,
                startAngle = -90f,
                sweepAngle = 360f * progress.coerceIn(0f, 1f),
                useCenter = false,
                style = stroke

            )
        }
        Icon(
            painter = painterResource(iconRes),
            contentDescription = null,
            tint = trackColor,
            modifier = Modifier.size(42.dp)
        )
    }
}