package com.example.myhealth.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myhealth.R
import com.example.myhealth.data.Day
import java.time.LocalDate

@Composable
fun SleepSectionContent(
    modifier: Modifier = Modifier, day: Day, goalSleep: Float
) {
    val currentSleep by remember { mutableFloatStateOf(day.calcBadTime()) }

    Row(
        modifier.fillMaxWidth().padding(8.dp).height(60.dp),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = " ${stringResource(R.string.sleep_count)}: ${day.bedTime.values.reduce { acc, i -> acc + i }}",
            minLines = 2,
            modifier = modifier.padding(top = 15.dp),
            style = MaterialTheme.typography.bodyLarge,
        )

        VerticalDivider(thickness = 6.dp, color = Color.Transparent)
        Box(modifier, Alignment.Center) {
            val str: String
            val color: Color
            if (currentSleep < goalSleep) {
                str =
                    "${stringResource(R.string.lack_of_sleep)} ${goalSleep - currentSleep} часов"
                color = Color.Red
            } else {
                str = "${stringResource(R.string.sleep_time)}: $currentSleep"
                color = Color.Blue
            }
            LinearProgressIndicator(
                progress = currentSleep / goalSleep,
                color = color,
                strokeCap = StrokeCap.Round,
                modifier = modifier.padding(2.dp).fillMaxHeight()
            )

            Text(
                str,
                style = androidx.compose.material3.MaterialTheme.typography.bodySmall,
                minLines = 2,
                modifier = modifier.padding(4.dp).fillMaxWidth().align(Alignment.CenterEnd),
                textAlign = TextAlign.Center
            )
        }
    }
}


@Composable
@Preview(showBackground = true)
fun SleepSectionContentPreview() {
    SleepSectionContent(Modifier, Day(LocalDate.now()), 8f)
}