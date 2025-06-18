package com.example.myhealth.presentation.dashboard

import androidx.compose.foundation.layout.size
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.myhealth.R
import com.example.myhealth.ui.theme.PrimaryBlue

@Composable
fun WaterFab(onClick: () -> Unit) {
    FloatingActionButton(
        onClick = onClick,
        containerColor = PrimaryBlue,
        elevation = FloatingActionButtonDefaults.elevation(6.dp),
        modifier = Modifier.size(56.dp)
    ) {
        Icon(painterResource(R.drawable.ic_water), null, tint = Color.White)
    }
}