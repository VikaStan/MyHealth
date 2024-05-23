package com.example.myhealth.Screens

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModel
import com.example.myhealth.models.DiaryViewModel
import com.example.myhealth.ui.components.DatePickerWithDialog
import com.example.myhealth.ui.theme.MyHealthTheme

@Composable
fun Stats(name: String, modifier: Modifier = Modifier) {

    //if (Screen.Stats.dialog.value) DatePickerWithDialog(modifier,diaryModel) TODO
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun StatsPreview() {
    MyHealthTheme {
        SleepAdd("Stats")
    }
}