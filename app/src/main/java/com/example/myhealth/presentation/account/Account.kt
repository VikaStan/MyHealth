package com.example.myhealth.presentation.account

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.myhealth.R
import com.example.myhealth.presentation.account.components.SettingsDropdown
import com.example.myhealth.presentation.account.components.TargetInfoCard
import com.example.myhealth.ui.theme.BackBlue
import com.example.myhealth.ui.theme.LightBlue
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
            /* ---------------------- HEADER + NAME ------------------ */
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = LightBlue),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(24.dp)
                ) {
                    Icon(      // аватар
                        painterResource(R.drawable.ic_avatar),
                        contentDescription = null,
                        tint = PrimaryBlue,
                        modifier = Modifier.size(72.dp)
                    )
                    Spacer(Modifier.height(12.dp))
                    /* -----------------  EDITABLE NAME  ---------------- */
                    OutlinedTextField(
                        value = state.name,
                        onValueChange = viewModel::onNameChange,
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.White, RoundedCornerShape(8.dp)),
                        placeholder = { Text("Имя", textAlign = TextAlign.Center) }
                    )
                }
            }

            MetricCard(
                icon = R.drawable.ic_age,
                placeholder = "Возраст",
                value = if (state.age == 0) "" else state.age.toString(),
                onValueChange = viewModel::onAgeChange,
            )

            /* ------------------ AGE  •  HEIGHT  •  WEIGHT ------------------ */
            MetricCard(
                icon = R.drawable.ic_ruler,
                placeholder = "Рост",
                value = if (state.height == 0) "" else state.height.toString(),
                onValueChange = viewModel::onHeightChange,
                suffix = "см"
            )

            MetricCard(
                icon = R.drawable.ic_weight,
                placeholder = "Вес",
                value = if (state.weight == 0) "" else state.weight.toString(),
                onValueChange = viewModel::onWeightChange,
                suffix = "кг"
            )

            TargetInfoCard("Цель по воде", "${state.waterTarget} мл")
            TargetInfoCard("Цель по калориям", "${state.caloriesTarget} ккал")

            Spacer(Modifier.padding(bottom = 72.dp))
        }
    }
}

/* ------------------ AGE  •  HEIGHT  •  WEIGHT ------------------ */
@Composable
fun MetricCard(
    @DrawableRes icon: Int,
    placeholder: String,
    value: String,
    onValueChange: (String) -> Unit,
    suffix: String? = null
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(16.dp)
        ) {
            Icon(painterResource(icon), null, tint = PrimaryBlue)
            Spacer(Modifier.width(16.dp))
            OutlinedTextField(
                value = value,
                onValueChange = onValueChange,
                singleLine = true,
                modifier = Modifier.weight(1f),
                placeholder = { Text(placeholder) }
            )
            suffix?.let { Text(it, color = Color.Gray, modifier = Modifier.padding(start = 8.dp)) }
        }
    }
}