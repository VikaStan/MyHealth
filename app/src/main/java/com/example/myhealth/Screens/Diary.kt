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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.myhealth.R
import com.example.myhealth.models.DiaryViewModel
import com.example.myhealth.ui.components.CalendarItem
import com.example.myhealth.ui.components.DatePickerWithDialog
import com.example.myhealth.ui.components.ExpandableSection
import com.example.myhealth.ui.components.FoodSectionContent
import com.example.myhealth.ui.components.SleepSectionContent
import com.example.myhealth.ui.theme.MyHealthTheme
import kotlinx.coroutines.launch


@RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
@Composable
fun Diary(
    modifier: Modifier = Modifier,
    diaryViewModel: DiaryViewModel = viewModel(),
    navHostController: NavHostController
) {

    diaryViewModel.navHostController = navHostController

    if (Screen.Diary.dialog.value) DatePickerWithDialog(modifier, diaryViewModel)

    CalendarList(modifier, diaryViewModel)

}

@Composable
fun CalendarList(modifier: Modifier, model: DiaryViewModel) {
    val dayList by model.days.collectAsState()
    val selectedDayIndex by model.selectedDayIndex.collectAsState()
    val listState = rememberLazyListState(initialFirstVisibleItemIndex = selectedDayIndex)
    val selectedDay by model.selectedDay.collectAsState()
    val coroutineScope = rememberCoroutineScope()

    LazyColumn(modifier.padding(horizontal = 8.dp)) {
        item { } // полоски с активностями
        item { //календарь
            LazyRow(
                modifier.padding(8.dp),
                state = listState
            ) {
                items(dayList) { day ->
                    CalendarItem(
                        modifier = modifier,
                        onItemClick = {
                            (model::selected)(dayList.indexOf(day))
                            coroutineScope.launch() {
                                listState.animateScrollToItem(selectedDayIndex)
                            }
                        },
                        day = day,
                        isSelected = dayList.indexOf(day) == selectedDayIndex
                    )
                    VerticalDivider(thickness = 10.dp)
                }
                coroutineScope.launch() {
                    listState.animateScrollToItem(selectedDayIndex)
                }
            }
        }

        item { // заполнение приемов пищи/сна
            ExpandableSection(
                modifier = modifier,
                title = R.string.breakfast_title,
                Icons.TwoTone.BreakfastDining,
                onAddClick = {
                    model.navHostController.navigate(Screen.FoodAdd.route)
                },
                content = { FoodSectionContent(eating = selectedDay.breakfast, goalCalories = selectedDay.goalCalories) }) //завтрак

            HorizontalDivider(thickness = 8.dp, color = Color.Transparent)
            ExpandableSection(
                modifier = modifier,
                title = R.string.lunch_title,
                Icons.TwoTone.LunchDining,
                onAddClick = {
                    model.navHostController.navigate(Screen.FoodAdd.route)
                },
                content = {
                    FoodSectionContent(eating = selectedDay.lunch, goalCalories = selectedDay.goalCalories)
                }) //обед

            HorizontalDivider(thickness = 8.dp, color = Color.Transparent)
            ExpandableSection(
                modifier = modifier,
                title = R.string.dinner_title,
                Icons.TwoTone.DinnerDining,
                onAddClick = {
                    model.navHostController.navigate(Screen.FoodAdd.route)
                },
                content = {
                    FoodSectionContent(eating = selectedDay.dinner, goalCalories = selectedDay.goalCalories)
                }) //ужин

            HorizontalDivider(thickness = 8.dp, color = Color.Transparent)
            ExpandableSection(
                modifier = modifier,
                title = R.string.sleep_title,
                Icons.TwoTone.Bedtime,
                onAddClick = {
                    model.navHostController.navigate(Screen.SleepAdd.route)
                },
                content = { SleepSectionContent(modifier, selectedDay,selectedDay.goalSleep) }) //сон
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DiaryPreview() {
    MyHealthTheme {
        // Diary("Diary", modifier = Modifier)
    }
}