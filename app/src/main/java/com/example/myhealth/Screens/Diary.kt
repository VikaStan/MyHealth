package com.example.myhealth.Screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.Bedtime
import androidx.compose.material.icons.twotone.BreakfastDining
import androidx.compose.material.icons.twotone.DinnerDining
import androidx.compose.material.icons.twotone.LunchDining
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.myhealth.R
import com.example.myhealth.data.DayOld
import com.example.myhealth.data.FoodTimeType
import com.example.myhealth.data.ProductOld
import com.example.myhealth.data.Sleep
import com.example.myhealth.models.DiaryViewModel
import com.example.myhealth.models.MainScreenViewModel
import com.example.myhealth.ui.components.CalendarItem
import com.example.myhealth.ui.components.DatePickerWithDialog
import com.example.myhealth.ui.components.ExpandableSection
import com.example.myhealth.ui.components.FoodSectionContent
import com.example.myhealth.ui.components.SleepSectionContent
import com.example.myhealth.ui.theme.MyHealthTheme
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch


@RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
@Composable
fun DiaryScreen(
    modifier: Modifier = Modifier,
    model: DiaryScreenViewModel = hiltViewModel(),
    mainModel: MainScreenViewModel,
    navHostController: NavHostController,
) {

    model.navHostController = navHostController
    model.getDayData(mainModel.dayOlds,mainModel.selectedDayIndex.collectAsState(), mainModel::selected)


    if (Screen.Diary.dialog.value) DatePickerWithDialog(modifier, model.selectedDayOld,mainModel::selected)

    LazyColumn(modifier.padding(horizontal = 8.dp)) {
        val items= SnapshotStateList<DayOld>()
        model.days.value.toCollection(items)
        item {
            CalendarList(modifier,items ,model.selectedDayIndex, model.onSelectedDay)
        }
        item { // заполнение приемов пищи/сна
            val selectedDay = model.selectedDayOld.collectAsState()
            FoodTimes(selectedDay.value, modifier, model::onAddFoodBtnClick,model::onSleepAddBtnClick)
        }
    }
}

@Composable
fun CalendarList(modifier: Modifier, dayOlds: SnapshotStateList<DayOld>, selectDayIndex: MutableStateFlow<Int>, onSelectedDay: (Int)-> Unit) {
    val selectedDayIndex by selectDayIndex.collectAsState()
    val listState = rememberLazyListState(initialFirstVisibleItemIndex = selectedDayIndex)
    val coroutineScope = rememberCoroutineScope()
    //календарь
    LazyRow(
        modifier.padding(8.dp),
        state = listState
    ) {
        items(dayOlds) { day ->
            CalendarItem(
                modifier = modifier,
                onItemClick = {
                    onSelectedDay(dayOlds.indexOf(day))
                    coroutineScope.launch() {
                        listState.animateScrollToItem(selectedDayIndex)
                    }
                },
                dayOld = day,
                isSelected = dayOlds.indexOf(day) == selectedDayIndex
            )
            VerticalDivider(thickness = 10.dp)
        }
        coroutineScope.launch() {
            listState.animateScrollToItem(selectedDayIndex)
        }
    }
}

@Composable
fun FoodTimes(
    dayOld: DayOld,
    modifier: Modifier,
    onAddFoodBtnClick: (String) -> Unit,
    onAddSleepBtnClick: () -> Unit
) {

    val brefProductOlds = SnapshotStateList<ProductOld>()
    dayOld.breakfast.productOlds.toCollection(brefProductOlds)
    val lunchProductOlds = SnapshotStateList<ProductOld>()
    dayOld.lunch.productOlds.toCollection(lunchProductOlds)
    val dinnerProductOlds = SnapshotStateList<ProductOld>()
    dayOld.dinner.productOlds.toCollection(dinnerProductOlds)
    val sleeps = SnapshotStateList<Sleep>()
    dayOld.bedTime.toCollection(sleeps)
    ExpandableSection(
        modifier = modifier,
        title = R.string.breakfast_title,
        Icons.TwoTone.BreakfastDining,
        onAddClick = {
            onAddFoodBtnClick(FoodTimeType.Breakfast.n)
        },
        content = {

            FoodSectionContent(
                productOlds =  brefProductOlds,
                goalCalories = dayOld.goalCalories
            )
        }) //завтрак

    HorizontalDivider(thickness = 8.dp, color = Color.Transparent)
    ExpandableSection(
        modifier = modifier,
        title = R.string.lunch_title,
        Icons.TwoTone.LunchDining,
        onAddClick = {
            onAddFoodBtnClick(FoodTimeType.Lunch.n)
        },
        content = {
            FoodSectionContent(
                productOlds = lunchProductOlds,
                goalCalories = dayOld.goalCalories
            )
        }) //обед

    HorizontalDivider(thickness = 8.dp, color = Color.Transparent)
    ExpandableSection(
        modifier = modifier,
        title = R.string.dinner_title,
        Icons.TwoTone.DinnerDining,
        onAddClick = {
            onAddFoodBtnClick(FoodTimeType.Dinner.n)
        },
        content = {
            FoodSectionContent(
                productOlds = dinnerProductOlds,
                goalCalories = dayOld.goalCalories
            )
        }) //ужин

    HorizontalDivider(thickness = 8.dp, color = Color.Transparent)
    ExpandableSection(
        modifier = modifier,
        title = R.string.sleep_title,
        Icons.TwoTone.Bedtime,
        onAddClick = {
            onAddSleepBtnClick()
        },
        content = {
            SleepSectionContent(
                modifier,
                sleeps,
                dayOld.goalSleep
            )
        })

}


@Preview(showBackground = true)
@Composable
fun DiaryPreview() {
    MyHealthTheme {
        // Diary("Diary", modifier = Modifier)
    }
}