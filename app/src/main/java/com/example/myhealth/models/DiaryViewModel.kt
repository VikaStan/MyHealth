package com.example.myhealth.models

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.myhealth.R
import com.example.myhealth.data.Day
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.count
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.util.stream.IntStream.range
import javax.inject.Inject

@HiltViewModel
class DiaryViewModel @Inject constructor():ViewModel(){

    lateinit var navHostController:NavHostController
    private val currDay:LocalDate = Instant.now().atZone(ZoneId.of("UTC+3")).toLocalDate()
    val days: MutableStateFlow<List<Day>> = MutableStateFlow(initDayList(getListSize()))
    var selectedDayIndex = MutableStateFlow(getListSize()-1)
    var selectedDay = MutableStateFlow(Day(
        date = currDay,
        foodCount = 1, //получать с базы
        totalCalories = 0, // рассчитывать и получать с бд
        //рассчитывать 2 параметра еще
    ))
    var selectedEatTimeName = MutableStateFlow("")

    fun selected(index: Int){
        updateDateToDayList()
        selectedDayIndex.value=index
        selectedDay = MutableStateFlow(days.value[index])
        selectedDay.value.calcBadTime()
        selectedDay.value.updateAllCount()
    }

    private fun updateDateToDayList(){
        days.value[selectedDayIndex.value].breakfast = selectedDay.value.breakfast
        days.value[selectedDayIndex.value].lunch = selectedDay.value.lunch
        days.value[selectedDayIndex.value].dinner = selectedDay.value.dinner
        days.value[selectedDayIndex.value].bedTime = selectedDay.value.bedTime
    }
    private fun getListSize():Int{
        return currDay.dayOfMonth + currDay.minusMonths(1).month.length(currDay.isLeapYear)
    }
    private fun initDayList(count:Int):List<Day>{
        val list: MutableList<Day> = emptyList<Day>().toMutableList()
        for (i in range(1,count+1)){
            val thisMouth = i > currDay.minusMonths(1).month.length(currDay.isLeapYear)
            val day = if (i<=currDay.minusMonths(1).month.length(currDay.isLeapYear)) currDay.minusMonths(1).withDayOfMonth(i)
            else currDay.withDayOfMonth(i - currDay.minusMonths(1).month.length(currDay.isLeapYear))

            list.add(
                Day(
                    date = day,
                    foodCount = 1, //получать с базы
                    totalCalories = 0, // рассчитывать и получать с бд
                                    //рассчитывать 2 параметра еще
                    ))
        }
        list.forEach { it.updateAllCount() }
        return list
    }

}

