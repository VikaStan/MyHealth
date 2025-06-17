package com.example.myhealth.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.myhealth.models.MainScreenViewModel
import com.example.myhealth.data.DayOld
import com.example.myhealth.data.datasource.local.entity.MealEntity
import com.example.myhealth.models.DiaryScreenViewModel
import com.example.myhealth.presentation.diary.MealType
import com.example.myhealth.ui.components.CalendarItem
import com.example.myhealth.ui.theme.MyHealthTheme
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch


@RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
@Composable
fun DiaryScreen(
    navHostController: NavHostController,
    model: DiaryScreenViewModel = hiltViewModel(),
    mainModel: MainScreenViewModel = hiltViewModel()
) {

    model.navHostController = navHostController
    LaunchedEffect(Unit) {
        model.getDayData(
            mainModel.dayOlds,
            mainModel.selectedDayIndex.collectAsState(),
            mainModel::selected
        )
    }

    val mealsToday by model.mealsToday.collectAsState()
    val totalCalories = model.totalCaloriesToday
    val caloriesTarget = model.caloriesTarget
    val proteins = model.totalProteinsToday
    val fats = model.totalFatsToday
    val carbs = model.totalCarbsToday

    val breakfastMeals = model.breakfastMeals
    val lunchMeals = model.lunchMeals
    val dinnerMeals = model.dinnerMeals

    Column(Modifier.fillMaxSize().background(Color(0xFFE8F4FF))) {
        // 1. Верхний блок — калории и БЖУ
        Card(
            Modifier
                .fillMaxWidth()
                .padding(12.dp),
            shape = RoundedCornerShape(20.dp)
        ) {
            Row(
                Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Круг калорий
                Box(Modifier.size(70.dp)) {
                    CircularProgressIndicator(
                        progress = (totalCalories.toFloat() / caloriesTarget).coerceIn(0f, 1f),
                        strokeWidth = 7.dp,
                        color = Color(0xFF6098FF)
                    )
                    Text(
                        "$totalCalories/\n$caloriesTarget",
                        Modifier.align(Alignment.Center),
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
                Spacer(Modifier.width(20.dp))
                Column {
                    Text("белки   $proteins г")
                    Text("жиры    $fats г")
                    Text("углеводы $carbs г")
                }
            }
        }
        // 2. Три карточки приёмов пищи
        MealSection("завтрак", breakfastMeals) { model.onAddMeal(MealType.BREAKFAST) }
        MealSection("обед", lunchMeals) { model.onAddMeal(MealType.LUNCH) }
        MealSection("ужин", dinnerMeals) { model.onAddMeal(MealType.DINNER) }

        Spacer(Modifier.weight(1f))
        // Нижняя навигация, если нужно
        // MyBottomNavigation()
    }
}

@Composable
fun MealSection(
    mealName: String,
    meals: List<MealEntity>,
    onAddMeal: () -> Unit
) {
    Card(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        shape = RoundedCornerShape(20.dp)
    ) {
        Column {
            Row(
                Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    mealName,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.weight(1f)
                )
                IconButton(onClick = onAddMeal) {
                    Icon(Icons.Default.Add, contentDescription = "Добавить блюдо", tint = Color(0xFF6098FF))
                }
            }
            if (meals.isNotEmpty()) {
                Column(Modifier.padding(start = 24.dp, bottom = 12.dp)) {
                    meals.forEach {
                        Text("${it.name} — ${it.calories} ккал")
                    }
                }
            }
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


@Preview(showBackground = true)
@Composable
fun DiaryPreview() {
    MyHealthTheme {
        // Diary("Diary", modifier = Modifier)
    }
}