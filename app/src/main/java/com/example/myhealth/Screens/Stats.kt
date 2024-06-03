package com.example.myhealth.Screens

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.tooling.preview.Preview
import com.example.myhealth.data.Day
import com.example.myhealth.models.MainScreenViewModel
import com.example.myhealth.models.StatsViewModel
import com.example.myhealth.ui.components.ActivityRings
import com.example.myhealth.ui.theme.MyHealthTheme

@Composable
fun Stats(mainModel: MainScreenViewModel,
          model: StatsViewModel= mainModel.statsViewModel
) {

    //if (Screen.Stats.dialog.value) DatePickerWithDialog(modifier,diaryModel) TODO
    model.getDayData(mainModel.days,mainModel.selectedDayIndex.collectAsState(), mainModel::selected)
    LaunchedEffect(key1=Unit){
        model.updateProgress()
    }





    Column {
        ActivityRings(0f,model.progressCalories.floatValue,model.progressSleep.floatValue)
    }
}

@Preview(showBackground = true)
@Composable
fun StatsPreview() {
    MyHealthTheme {
        //SleepAdd("Stats")
    }
}