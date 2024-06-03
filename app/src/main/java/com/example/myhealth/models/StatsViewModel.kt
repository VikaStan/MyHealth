package com.example.myhealth.models

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
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
    var days =  mutableStateListOf<Day>()
    var selectedDayIndex = MutableStateFlow(0)

    val progressCalories =  mutableFloatStateOf(selectedDay.value.totalCalories.toFloat()/selectedDay.value.goalCalories)
    val progressSleep = mutableFloatStateOf(selectedDay.value.totalSleep/selectedDay.value.goalSleep)
    var onSelectedDay:(Int)->Unit = {}
    fun getDayData(days: List<Day>, selectedDayIndex: State<Int>, onSelect:(Int) -> Unit){
        days.toCollection(this.days)
        this.selectedDayIndex.value=selectedDayIndex.value
        this.selectedDay.value= days[selectedDayIndex.value]
        this.onSelectedDay=onSelect
        selectedDay.value.updateAllCount()
        updateProgress()
    }
    fun updateProgress(){
        progressCalories.floatValue = selectedDay.value.totalCalories.toFloat()/selectedDay.value.goalCalories
        progressSleep.floatValue = selectedDay.value.totalSleep/selectedDay.value.goalSleep
    }
}