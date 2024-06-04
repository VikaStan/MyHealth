package com.example.myhealth.Screens

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.myhealth.models.AccountViewModel
import com.example.myhealth.models.MainScreenViewModel
import com.example.myhealth.ui.theme.MyHealthTheme

@Composable
fun Account(
    mainModel: MainScreenViewModel,
    model: AccountViewModel = mainModel.accountViewModel
) {
    Text(
        text = "Hello !",
    )
}

@Preview(showBackground = true)
@Composable
fun AccountPreview() {
    MyHealthTheme {
        //Account("Account")
    }
}