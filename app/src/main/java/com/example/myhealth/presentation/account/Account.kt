package com.example.myhealth.presentation.account

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cake
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.MonitorWeight
import androidx.compose.material.icons.filled.Straighten
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.myhealth.presentation.account.components.EditableField
import com.example.myhealth.presentation.account.components.ProfileHeader
import com.example.myhealth.presentation.account.components.SettingsDropdown
import com.example.myhealth.presentation.account.components.TargetInfoCard
import com.example.myhealth.ui.theme.BackBlue
import com.example.myhealth.ui.theme.PrimaryBlue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Account(
    navController: NavHostController,
    viewModel: AccountViewModel = hiltViewModel()
) {
    val state = viewModel.uiState

    Scaffold(
        containerColor = BackBlue,
        topBar = {
            TopAppBar(
                title = {},
                actions = {
                    SettingsDropdown(
                        onLogout = { viewModel.logout() },
                        onDelete = { viewModel.deleteAccount() }
                    )
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color.Transparent
                )
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                text = { Text("Сохранить") },
                icon = { Icon(Icons.Default.Check, null) },
                onClick = viewModel::onSave,
                containerColor = PrimaryBlue,
                contentColor = Color.White
            )
        }
    ) { padding ->
        Column(
            Modifier
                .padding(padding)
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            ProfileHeader(state.name)

            EditableField(
                icon = Icons.Default.Cake,
                label = "Возраст",
                value = if (state.age == 0) "" else state.age.toString(),
                onValueChange = viewModel::onAgeChange
            )

            EditableField(
                icon = Icons.Default.Straighten,
                label = "Рост",
                value = if (state.height == 0) "" else state.height.toString(),
                suffix = "см",
                onValueChange = viewModel::onHeightChange
            )

            EditableField(
                icon = Icons.Default.MonitorWeight,
                label = "Вес",
                value = if (state.weight == 0) "" else state.weight.toString(),
                suffix = "кг",
                onValueChange = viewModel::onWeightChange
            )

            TargetInfoCard("Цель по воде", "${state.waterTarget} мл")
            TargetInfoCard("Цель по калориям", "${state.caloriesTarget} ккал")

            Spacer(Modifier.padding(bottom = 72.dp))
        }
    }
}