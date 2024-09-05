package com.example.myhealth.models

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.myhealth.data.DayOld
import com.example.myhealth.data.Person
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

    lateinit var diaryModel: DiaryViewModel
    lateinit var foodAddViewModel: FoodAddViewModel
    lateinit var sleepAddViewModel: SleepAddViewModel
    lateinit var statsViewModel: StatsViewModel
    lateinit var accountViewModel: AccountViewModel

    private val currDay: LocalDate = Instant.now().atZone(ZoneId.of("UTC+3")).toLocalDate()
    var dayOlds = mutableStateListOf<DayOld>()
        get() {
            field.map {
                it.goalCalories = person.value?.caloriesGoal?.toInt() ?: 1000
                it.goalSleep = person.value?.sleepGoal ?: 8f
            }
            return field
        }
    var selectedDayIndex = MutableStateFlow(getListSize() - 1)
    var selectedDayOld = MutableStateFlow(
        DayOld(
            date = currDay,
            foodCount = 1, //получать с базы
            totalCalories = 0, // рассчитывать и получать с бд
            //рассчитывать 2 параметра еще
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


    init {
        initDayList(getListSize()).toCollection(dayOlds)
    }

    fun initiate(
        diaryModel: DiaryViewModel,
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

    private fun initDayList(count: Int): List<DayOld> {
        val list: MutableList<DayOld> = emptyList<DayOld>().toMutableList()
        for (i in IntStream.range(1, count + 1)) {
            val day =
                if (i <= currDay.minusMonths(1).month.length(currDay.isLeapYear)) currDay.minusMonths(
                    1
                ).withDayOfMonth(i)
                else currDay.withDayOfMonth(i - currDay.minusMonths(1).month.length(currDay.isLeapYear))

            list.add(
                DayOld(
                    date = day,
                    foodCount = 1,
                    totalCalories = 0,
                    goalSleep = person.value?.sleepGoal ?: 8f,
                    goalCalories = person.value?.caloriesGoal?.toInt() ?: 1000
                )
            )
        }
        list.forEach { it.updateAllCount() }

        selectedDayOld = MutableStateFlow(list.last())
        return list
    }

    private fun updateDataInDayList() {
        dayOlds[selectedDayIndex.value].breakfast = selectedDayOld.value.breakfast
        dayOlds[selectedDayIndex.value].lunch = selectedDayOld.value.lunch
        dayOlds[selectedDayIndex.value].dinner = selectedDayOld.value.dinner
        dayOlds[selectedDayIndex.value].bedTime = selectedDayOld.value.bedTime
    }

    fun selected(index: Int) {
        updateDataInDayList()
        selectedDayIndex.value = index
        selectedDayOld.value = dayOlds[index]
        selectedDayOld.value.breakfast = dayOlds[index].breakfast
        selectedDayOld.value.lunch = dayOlds[index].lunch
        selectedDayOld.value.dinner = dayOlds[index].dinner
        selectedDayOld.value.bedTime = dayOlds[index].bedTime
        selectedDayOld.value.updateAllCount()
    }
}