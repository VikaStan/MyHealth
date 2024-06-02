package com.example.myhealth.Screens

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import com.example.myhealth.data.Day
import com.example.myhealth.models.DiaryViewModel
import com.example.myhealth.models.StatsViewModel
import com.example.myhealth.ui.components.ActivityRings
import com.example.myhealth.ui.components.DatePickerWithDialog
import com.example.myhealth.ui.theme.MyHealthTheme

@Composable
fun Stats(diaryViewModel: DiaryViewModel,
          model: StatsViewModel = hiltViewModel()
) {

    //if (Screen.Stats.dialog.value) DatePickerWithDialog(modifier,diaryModel) TODO

    LaunchedEffect(key1=Unit){
        //model.getSelectedDay(diaryViewModel)
    }
    model.selectedDay =  diaryViewModel.selectedDay.collectAsState() as MutableState<Day>




    Column {
        ActivityRings(0f,model.progressCalories,model.progressSleep)
    }
}

@Preview(showBackground = true)
@Composable
fun StatsPreview() {
    MyHealthTheme {
        //SleepAdd("Stats")
    }
}