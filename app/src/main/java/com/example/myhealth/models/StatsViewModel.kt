package com.example.myhealth.models

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.myhealth.data.Day
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import javax.inject.Inject
import kotlin.math.max

@HiltViewModel
class StatsViewModel @Inject constructor() : ViewModel() {
    var selectedDay = mutableStateOf(Day(Instant.now().atZone(ZoneId.of("UTC+3")).toLocalDate()))
    var days = mutableStateListOf<Day>()
    var daysFiltered = mutableStateListOf<Day>()
    var selectedDayIndex = MutableStateFlow(getListSize() - 1)
    var dateSelectionBtn = mutableStateMapOf(
        DateSelection.Day to true,
        DateSelection.Week to false,
        DateSelection.Mounth to false,
    )
    var strike = mutableIntStateOf(0)
    var bestStrike = mutableIntStateOf(0)


    val progressCalories =
        mutableFloatStateOf(selectedDay.value.totalCalories.toFloat() / selectedDay.value.goalCalories)
    val progressSleep =
        mutableFloatStateOf(selectedDay.value.totalSleep / selectedDay.value.goalSleep)
    var onSelectedDay: (Int) -> Unit = {}
    fun getDayData(days: List<Day>, selectedDayIndex: State<Int>, onSelect: (Int) -> Unit) {
        this.days.clear()
        days.toCollection(this.days)
        strike.intValue = 0
        bestStrike.intValue = 0
        this.selectedDayIndex.value = selectedDayIndex.value
        this.selectedDay.value = days[selectedDayIndex.value]
        this.onSelectedDay = onSelect
        selectedDay.value.updateAllCount()
        updateProgress()
    }

    fun onDateSelection(sel: DateSelection) {
        when (sel) {
            DateSelection.Day -> {
                dateSelectionBtn[DateSelection.Day] = true
                dateSelectionBtn[DateSelection.Week] = false
                dateSelectionBtn[DateSelection.Mounth] = false
                daysFiltered.clear()
                daysFiltered.add(selectedDay.value)
            }

            DateSelection.Week -> {
                dateSelectionBtn[DateSelection.Day] = false
                dateSelectionBtn[DateSelection.Week] = true
                dateSelectionBtn[DateSelection.Mounth] = false
                daysFiltered.clear()
                val startIndex =
                    if ((selectedDayIndex.value - 7) >= 0) selectedDayIndex.value - 7 else 0
                daysFiltered.addAll(days.subList(startIndex, selectedDayIndex.value))
            }

            DateSelection.Mounth -> {
                dateSelectionBtn[DateSelection.Day] = false
                dateSelectionBtn[DateSelection.Week] = false
                dateSelectionBtn[DateSelection.Mounth] = true
                daysFiltered.clear()
                val startIndex =
                    if ((selectedDayIndex.value - 30) >= 0) selectedDayIndex.value - 30 else 0
                daysFiltered.addAll(days.subList(startIndex, selectedDayIndex.value))
            }
        }
        updateProgress()
    }

    fun updateProgress() {
        progressCalories.floatValue = (daysFiltered.fold(0) { a, b -> a + b.totalCalories }
            .toFloat() / daysFiltered.size) / selectedDay.value.goalCalories
        progressSleep.floatValue = (daysFiltered.fold(0f) { a, b -> a + b.totalSleep }
            .toFloat() / daysFiltered.size) / selectedDay.value.goalSleep

        var maxVal = mutableListOf(0)
        days.forEach {
            if (it.isStrike) {
                strike.intValue++
            } else {
                strike.intValue = 0
                maxVal.add(strike.intValue)
            }
        }
        bestStrike.intValue = maxVal.max()
    }

    private fun getListSize(): Int {
        val currDay: LocalDate = Instant.now().atZone(ZoneId.of("UTC+3")).toLocalDate()
        return currDay.dayOfMonth + currDay.minusMonths(1).month.length(currDay.isLeapYear)
    }

    enum class DateSelection {
        Day,
        Week,
        Mounth
    }
}