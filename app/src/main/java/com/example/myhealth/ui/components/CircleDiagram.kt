package com.example.myhealth.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


@Composable
fun ActivityRings(
    moveProgress: Float,  // Progress for the red ring (0..1)
    exerciseProgress: Float,  // Progress for the green ring (0..1)
    standProgress: Float,  // Progress for the blue ring (0..1)
    componentSize: Int = 200,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ){
    Canvas(modifier = modifier.size(componentSize.dp)) {
        val size = size.minDimension
        val strokeWidth = size * 0.1f

        // Calculate radii for each ring
        val radii = listOf(
            size * 0.4f,
            size * 0.55f,
            size * 0.7f
        )

        // Colors for each ring
        val colors = listOf(
            Color.Red,
            Color.Green,
            Color.Blue
        )

        // Progress for each ring
        val progresses = listOf(
            moveProgress,
            exerciseProgress,
            standProgress
        )

        // Draw the background rings
        for (i in radii.indices) {
            drawCircle(
                color = colors[i].copy(alpha = 0.35f),
                center = center,
                radius = radii[i],
                style = Stroke(width = strokeWidth)
            )
        }

        // Draw the progress rings
        for (i in radii.indices) {
            drawArc(
                color = colors[i],
                startAngle = -90f,
                sweepAngle = 360 * progresses[i],
                useCenter = false,
                topLeft = Offset(center.x - radii[i], center.y - radii[i]),
                size = Size(radii[i] * 2, radii[i] * 2),
                style = Stroke(width = strokeWidth)
            )
        }
    }
}
}

@Preview(showBackground = true)
@Composable
fun ActivityRingsPreview() {
    MaterialTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            ActivityRings(
                moveProgress = 0.75f,
                exerciseProgress = 0.5f,
                standProgress = 0.9f,
                modifier = Modifier.size(200.dp)
            )
        }
    }
}