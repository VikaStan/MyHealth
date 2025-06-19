package com.example.myhealth.presentation.statistics.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.myhealth.presentation.statistics.StatsTab
import com.example.myhealth.ui.theme.CardBlue
import com.example.myhealth.ui.theme.PrimaryBlue

@Composable
fun SegmentedTabRow(
    selected: StatsTab,
    onSelect: (StatsTab) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier
            .background(CardBlue, RoundedCornerShape(24.dp))
            .padding(4.dp)
    ) {
        StatsTab.entries.forEach { tab ->
            val selectedBg = if (selected == tab) PrimaryBlue else Color.Transparent
            val textColor = if (selected == tab) Color.White else PrimaryBlue

            Box(
                Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(20.dp))
                    .background(selectedBg)
                    .clickable { onSelect(tab) }
                    .padding(vertical = 6.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = if (tab == StatsTab.ACTIVITY) "Активность" else "Гидратация",
                    style = MaterialTheme.typography.bodyMedium,
                    color = textColor
                )
            }
        }
    }
}