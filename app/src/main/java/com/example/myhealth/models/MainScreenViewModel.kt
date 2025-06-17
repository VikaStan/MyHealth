package com.example.myhealth.models

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.myhealth.data.Person
import com.example.myhealth.domain.models.Day
import com.example.myhealth.domain.models.MealTime
import com.example.myhealth.presentation.diary.MealType
import com.example.myhealth.presentation.statistics.StatsViewModel
import com.example.myhealth.room.HealthRoomDb
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.util.stream.IntStream
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(
     val mainDB: HealthRoomDb
) : ViewModel() {

    lateinit var diaryModel: DiaryScreenViewModel
    lateinit var foodAddViewModel: FoodAddViewModel
    lateinit var sleepAddViewModel: SleepAddViewModel
    lateinit var statsViewModel: StatsViewModel
    lateinit var accountViewModel: AccountViewModel

    private val currDay: LocalDate = Instant.now().atZone(ZoneId.of("UTC+3")).toLocalDate()
    var dayOlds = mutableStateListOf<Day>()
        get() {
            field.map {
                it.goalCalories = person.value?.caloriesGoal?.toInt() ?: 1000
                it.goalSleep = person.value?.sleepGoal ?: 8f
            }
            return field
        }
    var selectedDayIndex = MutableStateFlow(getListSize() - 1)
    var selectedDayOld = MutableStateFlow(
        Day(
            date = currDay,
            goalCalories = 0,
            goalSleep = 0f,
            mealTimeList = mutableListOf(
                MealTime(0, MealType.BREAKFAST.value, 0, 0, 0, mutableListOf()),
                MealTime(1, MealType.LUNCH.value, 0, 0, 0, mutableListOf()),
                MealTime(2, MealType.DINNER.value, 0, 0, 0, mutableListOf())
            ),
            sleepTimeList = mutableListOf()
        )
    )
    lateinit var person: LiveData<Person>

    private fun getPerson() = viewModelScope.launch {
        person = liveData {
            mainDB.personDao.getPerson()
        }
    }

    init {
        getPerson()
        initDayList(getListSize()).toCollection(dayOlds)
    }

    var inSystem = MutableStateFlow(false)
        set(value) {
            dayOlds.clear()
            initDayList(getListSize()).toCollection(dayOlds)
            field = value
        }

    fun initiate(
        diaryModel: DiaryScreenViewModel,
        foodAddViewModel: FoodAddViewModel,
        sleepAddViewModel: SleepAddViewModel,
        statsViewModel: StatsViewModel,
        accountViewModel: AccountViewModel,
    ) {
        this.diaryModel = diaryModel
        this.foodAddViewModel = foodAddViewModel
        this.sleepAddViewModel = sleepAddViewModel
        this.statsViewModel = statsViewModel
        this.accountViewModel = accountViewModel
        //получать, в системе ли
    }

    fun updatePersonDate(person: Person) {
        this.person = MutableLiveData(person)
        inSystem = MutableStateFlow(true)
    }

    private fun getListSize(): Int {
        return currDay.dayOfMonth + currDay.minusMonths(1).month.length(currDay.isLeapYear)
    }

    private fun initDayList(count: Int): List<Day> {
        val list: MutableList<Day> = emptyList<Day>().toMutableList()
        for (i in IntStream.range(1, count + 1)) {
            val day =
                if (i <= currDay.minusMonths(1).month.length(currDay.isLeapYear)) currDay.minusMonths(
                    1
                ).withDayOfMonth(i)
                else currDay.withDayOfMonth(i - currDay.minusMonths(1).month.length(currDay.isLeapYear))

            list.add(
                Day(
                    date = day,
                    goalSleep = person.value?.sleepGoal ?: 8f,
                    goalCalories = person.value?.caloriesGoal?.toInt() ?: 1000,
                    mealTimeList = mutableListOf(
                        MealTime(0, MealType.BREAKFAST.value, 0, 0, 0, mutableListOf()),
                        MealTime(1, MealType.LUNCH.value, 0, 0, 0, mutableListOf()),
                        MealTime(2, MealType.DINNER.value, 0, 0, 0, mutableListOf())
                    ),
                    sleepTimeList = mutableListOf()
                )
            )
        }
        list.forEach { it.updateAllCount() }

        selectedDayOld = MutableStateFlow(list.last())
        return list
    }

    private fun updateDataInDayList() {
        dayOlds[selectedDayIndex.value].mealTimeList = selectedDayOld.value.mealTimeList
        dayOlds[selectedDayIndex.value].sleepTimeList = selectedDayOld.value.sleepTimeList
    }

    fun selected(index: Int) {
        updateDataInDayList()
        selectedDayIndex.value = index
        selectedDayOld.value = dayOlds[index]
        selectedDayOld.value.mealTimeList = dayOlds[index].mealTimeList
        selectedDayOld.value.sleepTimeList = dayOlds[index].sleepTimeList
        selectedDayOld.value.updateAllCount()
    }
}