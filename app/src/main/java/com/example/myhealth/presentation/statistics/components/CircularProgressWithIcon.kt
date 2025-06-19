package com.example.myhealth.presentation.statistics.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.myhealth.ui.theme.PrimaryBlue

@Composable
fun CircularProgressWithIcon(
    progress: Float,
    iconRes: Int? = null,
    progressColor: Color = PrimaryBlue,
    centerText: String? = null
) {
    Box(contentAlignment = Alignment.Center, modifier = Modifier.size(96.dp)) {
        Canvas(Modifier.size(96.dp)) {
            drawArc(
                color = progressColor.copy(alpha = .2f),
                startAngle = 0f,
                sweepAngle = 360f,
                useCenter = false,
                style = Stroke(10.dp.toPx(), cap = StrokeCap.Round)
            )
            drawArc(
                color = progressColor,
                startAngle = -90f,
                sweepAngle = 360f * progress,
                useCenter = false,
                style = Stroke(10.dp.toPx(), cap = StrokeCap.Round)
            )
        }
        if (iconRes != null) {
            Icon(
                painterResource(iconRes),
                null,
                tint = progressColor,
                modifier = Modifier.size(40.dp)
            )
        } else {
            Text(
                centerText ?: "",
                style = MaterialTheme.typography.bodyLarge,
                color = progressColor
            )
        }
    }
}