package com.example.myhealth.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myhealth.R
import com.example.myhealth.data.Day
import com.example.myhealth.data.Food
import java.time.LocalDate

@Composable
fun FoodSectionContent(
    modifier: Modifier = Modifier, eating: Food, goalCalories: Int
) {
    var cal = 0f
    eating.products.forEach { product ->
        cal += product.calories * (product.gramms / 100)
    }


val currentCalories by remember { mutableFloatStateOf(cal) }

Row(
modifier.fillMaxWidth().padding(8.dp).height(60.dp),
horizontalArrangement = Arrangement.SpaceAround,
verticalAlignment = Alignment.CenterVertically
) {
    Text(
        text = " ${stringResource(R.string.products)}: ${eating.products.size}",
        minLines = 2,
        modifier = modifier.padding(top = 20.dp),
        style = MaterialTheme.typography.bodyLarge,
    )

    VerticalDivider(thickness = 6.dp, color = Color.Transparent)
    //ProgressTextIndicator((eating.calories / maxCalories).toFloat(), {})
    Box(modifier, Alignment.Center) {
        val str: String
        val color: Color
        if (currentCalories > goalCalories) {
            str =
                "${stringResource(R.string.calories)} на ${currentCalories - goalCalories} больше "
            color = Color.Red
        } else {
            str = "${stringResource(R.string.calories)}: $currentCalories"
            color = Color.Green
        }
        LinearProgressIndicator(
            progress = currentCalories / goalCalories,
            color = color,
            strokeCap = StrokeCap.Round,
            modifier = modifier.padding(2.dp).fillMaxHeight()
        )

        Text(
            str,
            style = androidx.compose.material3.MaterialTheme.typography.bodySmall,
            minLines = 2,
            modifier = modifier.padding(4.dp).padding(top = 15.dp).fillMaxWidth()
                .align(Alignment.Center),
            textAlign = TextAlign.Center
        )
    }
    }
}

@Composable
fun ProgressTextIndicator(
    progress: Float,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var height by remember { mutableStateOf(0.dp) }

    val shape = RoundedCornerShape(8.dp)
    val density = LocalDensity.current
    Box(
        modifier = modifier.background(
            color = Color.Green,
            shape = shape,
        ).clip(shape),
    ) {
        Button(
            modifier = Modifier.fillMaxSize().onSizeChanged {
                height = density.run { it.height.toDp() }
            },
            contentPadding = PaddingValues(16.dp),
            enabled = false,
            onClick = onClick,
        ) {}
        LinearProgressIndicator(
            modifier = Modifier.fillMaxWidth().height(height),
            progress = progress,
            backgroundColor = Color.Transparent,
            color = Color.White.copy(alpha = 0.15f),
        )
        Text("Каллории", style = androidx.compose.material3.MaterialTheme.typography.bodyMedium)
    }
}


@Composable
@Preview(showBackground = true)
fun FoodSectionContentPreview() {
    FoodSectionContent(Modifier, Day(LocalDate.now()).breakfast, 10000)
}