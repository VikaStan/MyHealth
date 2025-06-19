package com.example.myhealth.presentation.account.components

import androidx.compose.foundation.layout.Box
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.example.myhealth.ui.theme.PrimaryBlue

@Composable
fun SettingsDropdown(
    onLogout: () -> Unit,
    onDelete: () -> Unit
) {
    val expanded = remember { mutableStateOf(false) }

    Box {
        IconButton(onClick = { expanded.value = true }) {
            Icon(Icons.Default.Settings, null, tint = PrimaryBlue)
        }
        DropdownMenu(expanded.value, { expanded.value = false }) {
            DropdownMenuItem(text = { Text("Выйти") }, onClick = onLogout)
            DropdownMenuItem(text = { Text("Удалить") }, onClick = onDelete)
        }
    }
}