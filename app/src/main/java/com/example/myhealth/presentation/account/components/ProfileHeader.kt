package com.example.myhealth.presentation.account.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.myhealth.R
import com.example.myhealth.ui.theme.CardBlue
import com.example.myhealth.ui.theme.PrimaryBlue

@Composable
fun ProfileHeader(name: String) {
    Column(
        Modifier
            .fillMaxWidth()
            .background(CardBlue, RoundedCornerShape(24.dp))
            .padding(vertical = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            painterResource(R.drawable.ic_avatar),
            contentDescription = null,
            tint = PrimaryBlue,
            modifier = Modifier.size(96.dp)
        )
        Spacer(Modifier.height(12.dp))
        Text(name.ifBlank { "Имя" }, style = MaterialTheme.typography.titleLarge)
    }
}