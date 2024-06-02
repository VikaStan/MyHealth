package com.example.myhealth.models

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import com.example.myhealth.data.Day
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import java.time.Instant
import java.time.ZoneId
import javax.inject.Inject

@HiltViewModel
class StatsViewModel @Inject constructor():ViewModel() {
     var selectedDay = mutableStateOf(Day(Instant.now().atZone(ZoneId.of("UTC+3")).toLocalDate()))

    val progressCalories by  mutableFloatStateOf(selectedDay.value.totalCalories.toFloat()/selectedDay.value.goalCalories)
    val progressSleep by mutableFloatStateOf(selectedDay.value.totalSleep/selectedDay.value.goalSleep)
    fun getSelectedDay(model: DiaryViewModel){
        selectedDay.value = model.selectedDay.value
    }
}