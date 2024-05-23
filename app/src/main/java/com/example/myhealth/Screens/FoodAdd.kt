package com.example.myhealth.Screens

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.myhealth.ui.theme.MyHealthTheme

@Composable
fun FoodAdd(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun FoodAddPreview() {
    MyHealthTheme {
        FoodAdd("FoodAdd")
    }
}