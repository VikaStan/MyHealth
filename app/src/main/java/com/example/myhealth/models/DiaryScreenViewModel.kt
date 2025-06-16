package com.example.myhealth.models

import androidx.compose.runtime.State
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.example.myhealth.screens.Screen
import com.example.myhealth.data.DayOld
import com.example.myhealth.data.datasource.local.entity.MealEntity
import com.example.myhealth.domain.repository.MealTimeRepository
import com.example.myhealth.presentation.diary.MealType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.util.stream.IntStream.range
import javax.inject.Inject

@HiltViewModel
class DiaryScreenViewModel @Inject constructor() : ViewModel() {

    lateinit var navHostController: NavHostController
    private val currDay: LocalDate = Instant.now().atZone(ZoneId.of("UTC+3")).toLocalDate()
    val days: MutableStateFlow<List<DayOld>> = MutableStateFlow(initDayList(getListSize()))
    val mealsToday: StateFlow<List<MealEntity>> = MealTimeRepository.getMealsForToday()
        .stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())
    val totalCaloriesToday: Int
        get() = mealsToday.value.sumOf { it.calories }

    val totalProteinsToday: Int
        get() = mealsToday.value.sumOf { it.proteins }

    val totalFatsToday: Int
        get() = mealsToday.value.sumOf { it.fats }

    val totalCarbsToday: Int
        get() = mealsToday.value.sumOf { it.carbs }

    val breakfastMeals: List<MealEntity>
        get() = mealsToday.value.filter { it.type == MealType.BREAKFAST }

    val lunchMeals: List<MealEntity>
        get() = mealsToday.value.filter { it.type == MealType.LUNCH }

    val dinnerMeals: List<MealEntity>
        get() = mealsToday.value.filter { it.type == MealType.DINNER }

    val caloriesTarget: Int
        get() = PersonRepository.getCaloriesTarget() // берёшь из профиля или настроек


    fun onAddMeal(type: MealType) {
        // открытие формы добавления блюда с выбранным типом (передавай type)
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
    var selectedEatTimeName = MutableStateFlow("")

    var onSelectedDay :(Int)->Unit = {}
    fun getDayData(dayOlds: List<DayOld>, selectedDayIndex: State<Int>, onSelect:(Int) -> Unit){
        this.days.value=dayOlds
        this.selectedDayIndex.value=selectedDayIndex.value
        this.selectedDayOld.value = dayOlds[selectedDayIndex.value]
        this.onSelectedDay=onSelect
        selectedDayOld.value.updateAllCount()
    }


    fun onAddFoodBtnClick(foodTimeName: String) {
        navHostController.navigate(Screen.FoodAdd.route + "/${foodTimeName}")
        selectedEatTimeName.value = foodTimeName //для последующего обновления списка продуктов
    }

    fun onSleepAddBtnClick() {
        navHostController.navigate(Screen.SleepAdd.route)
    }

    private fun updateDataInDayList() {
        days.value[selectedDayIndex.value].breakfast = selectedDayOld.value.breakfast
        days.value[selectedDayIndex.value].lunch = selectedDayOld.value.lunch
        days.value[selectedDayIndex.value].dinner = selectedDayOld.value.dinner
        days.value[selectedDayIndex.value].bedTime = selectedDayOld.value.bedTime
    }

    private fun getListSize(): Int {
        return currDay.dayOfMonth + currDay.minusMonths(1).month.length(currDay.isLeapYear)
    }

    private fun initDayList(count: Int): List<DayOld> {
        val list: MutableList<DayOld> = emptyList<DayOld>().toMutableList()
        for (i in range(1, count + 1)) {
            val thisMouth = i > currDay.minusMonths(1).month.length(currDay.isLeapYear)
            val day =
                if (i <= currDay.minusMonths(1).month.length(currDay.isLeapYear)) currDay.minusMonths(
                    1
                ).withDayOfMonth(i)
                else currDay.withDayOfMonth(i - currDay.minusMonths(1).month.length(currDay.isLeapYear))

            list.add(
                DayOld(
                    date = day,
                    foodCount = 2, //получать с базы
                    totalCalories = 0, // рассчитывать и получать с бд
                    //рассчитывать 2 параметра еще
                    goalSleep = 10f
                )
            )
        }
        list.forEach { it.updateAllCount() }

        selectedDayOld = MutableStateFlow(list.last())
        return list
    }


}

