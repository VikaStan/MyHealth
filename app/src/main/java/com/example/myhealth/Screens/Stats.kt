package com.example.myhealth.Screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bedtime
import androidx.compose.material.icons.filled.LocalDining
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.InputChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myhealth.R
import com.example.myhealth.data.Day
import com.example.myhealth.data.Product
import com.example.myhealth.models.MainScreenViewModel
import com.example.myhealth.models.StatsViewModel
import com.example.myhealth.ui.components.ActivityRings
import com.example.myhealth.ui.components.DatePickerWithDialog
import com.example.myhealth.ui.theme.MyHealthTheme

@RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
@Composable
fun Stats(
    mainModel: MainScreenViewModel,
    model: StatsViewModel = mainModel.statsViewModel
) {
    model.getDayData(
        mainModel.days,
        mainModel.selectedDayIndex.collectAsState(),
        mainModel::selected
    )
    LaunchedEffect(key1 = Unit) {
        model.onDateSelection(StatsViewModel.DateSelection.Day)
        model.updateProgress()
    }

    if (Screen.Stats.dialog.value) DatePickerWithDialog(
        Modifier,
        mainModel.selectedDay,
        mainModel::selected
    )

    LazyColumn(
        modifier = Modifier.padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Row {
                model.dateSelectionBtn[StatsViewModel.DateSelection.Day]?.let {
                    InputChip(
                        it,
                        onClick = { model.onDateSelection(StatsViewModel.DateSelection.Day) },
                        label = { Text(stringResource(R.string.day_btn)) },
                        modifier = Modifier.padding(horizontal = 4.dp)
                    )
                }
                model.dateSelectionBtn[StatsViewModel.DateSelection.Week]?.let {
                    InputChip(
                        it,
                        onClick = { model.onDateSelection(StatsViewModel.DateSelection.Week) },
                        label = { Text(stringResource(R.string.week_btn)) },
                        modifier = Modifier.padding(horizontal = 4.dp)
                    )
                }
                model.dateSelectionBtn[StatsViewModel.DateSelection.Mounth]?.let {
                    InputChip(
                        it,
                        onClick = { model.onDateSelection(StatsViewModel.DateSelection.Mounth) },
                        label = { Text(stringResource(R.string.mounth_btn)) },
                        modifier = Modifier.padding(horizontal = 4.dp)
                    )
                }
            }
        } //date range select
        item {
            CalendarList(
                Modifier,
                model.days,
                model.selectedDayIndex,
                mainModel::selected
            )
        } //calendar select
        item {
            ActivitySection(
                model.strike.intValue.toFloat(),
                model.progressCalories.floatValue,
                model.progressSleep.floatValue,
                model.selectedDay.value
            )
        } //activity ring section
        item {
            val products = SnapshotStateList<Product>()
            model.daysFiltered.forEach {
                it.breakfast.products.toCollection(products)
                it.lunch.products.toCollection(products)
                it.dinner.products.toCollection(products)
            }
            FoodSection(
                Modifier.fillParentMaxSize(),
                {},
                products
            )
        }//product section
        item { AverrageCaloriesSection(model.daysFiltered) } //averrage section
        item { StrikeSection(model.strike,model.bestStrike)} //series? section
    }
}

@Composable
fun StrikeSection(strike: State<Int>, bestStrike: State<Int>) {

        Column(
            modifier = Modifier.fillMaxWidth().padding(8.dp).background(
                color = MaterialTheme.colorScheme.secondaryContainer.copy(
                    alpha = .50f
                ), shape = RoundedCornerShape(8.dp)
            ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                stringResource(R.string.strike_title),
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Center
            )
            Row {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(stringResource(R.string.strike_title)+":")
                    Text(
                        "${strike.value}",
                        style = MaterialTheme.typography.bodySmall
                    )
                }

                VerticalDivider(thickness = 8.dp)
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(stringResource(R.string.best_strike) + ":")
                    Text(
                        "${bestStrike.value}",
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        }
}

@Composable
fun AverrageCaloriesSection(days: SnapshotStateList<Day>) {
    val breakfastProductCount by remember { mutableIntStateOf(days.fold(0) { a, b -> a + b.breakfast.products.size } / days.count { it.breakfast.products.isNotEmpty() }) }
    val lunchProductCount by remember { mutableIntStateOf(days.fold(0) { a, b -> a + b.lunch.products.size } / days.count { it.lunch.products.isNotEmpty() }) }
    val dinnerProductCount by remember { mutableIntStateOf(days.fold(0) { a, b -> a + b.dinner.products.size } / days.count { it.dinner.products.isNotEmpty() }) }

    val breakfastCalories by remember { mutableFloatStateOf(days.fold(0f) { a, b -> a + b.breakfast.getFoodTimeCalories() } / days.count { it.breakfast.products.isNotEmpty() }) }
    val lunchCalories by remember { mutableFloatStateOf(days.fold(0f) { a, b -> a + b.lunch.getFoodTimeCalories() } / days.count { it.lunch.products.isNotEmpty() }) }
    val dinnerCalories by remember { mutableFloatStateOf(days.fold(0f) { a, b -> a + b.dinner.getFoodTimeCalories() } / days.count { it.dinner.products.isNotEmpty() }) }


    Column(
        modifier = Modifier.fillMaxWidth().padding(8.dp).background(
            color = MaterialTheme.colorScheme.secondaryContainer.copy(
                alpha = .50f
            ), shape = RoundedCornerShape(8.dp)
        ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            stringResource(R.string.avr_calories_title),
            style = MaterialTheme.typography.titleMedium,
            textAlign = TextAlign.Center
        )
        Row {
            Column(
                Modifier.padding(4.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(stringResource(R.string.breakfast_title))
                Text(
                    stringResource(R.string.products) + ": $breakfastProductCount",
                    style = MaterialTheme.typography.bodySmall
                )
                HorizontalDivider(Modifier.width(120.dp))
                Text(
                    "$breakfastCalories " + stringResource(R.string.calories),
                    style = MaterialTheme.typography.bodySmall
                )
            }

            Column(
                Modifier.padding(4.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(stringResource(R.string.lunch_title))
                Text(
                    stringResource(R.string.products) + ": $lunchProductCount",
                    style = MaterialTheme.typography.bodySmall
                )
                HorizontalDivider(Modifier.width(100.dp))
                Text(
                    "$lunchCalories " + stringResource(R.string.calories),
                    style = MaterialTheme.typography.bodySmall
                )
            }

            Column(
                Modifier.padding(4.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(stringResource(R.string.dinner_title))
                Text(
                    stringResource(R.string.products) + ": $dinnerProductCount",
                    style = MaterialTheme.typography.bodySmall
                )
                HorizontalDivider(Modifier.width(100.dp))
                Text(
                    "$dinnerCalories " + stringResource(R.string.calories),
                    style = MaterialTheme.typography.bodySmall
                )
            }

        }
    }
}

@Composable
fun ActivitySection(
    comboProgress: Float,
    caloriesProgress: Float,
    sleepProgress: Float,
    day: Day,
) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(8.dp).background(
            color = MaterialTheme.colorScheme.secondaryContainer.copy(
                alpha = .50f
            ), shape = RoundedCornerShape(8.dp)
        ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        ActivityRings(
            comboProgress,
            caloriesProgress,
            sleepProgress,
            componentSize = 100,
            modifier = Modifier.padding(30.dp).wrapContentSize()
        )
        VerticalDivider(thickness = 8.dp, color = Color.Transparent)
        Column {
            Row {
                Icon(Icons.Default.LocalFireDepartment, "", tint = Color.Red)
                Text((comboProgress * 100).toInt().toString() + " дней", color = Color.Red)
            }

            Row {
                Icon(Icons.Default.LocalDining, "", tint = Color.Green)
                Text(
                    "${day.totalCalories}/${day.goalCalories}  ${stringResource(R.string.calories)}",
                    color = Color.Green
                )
            }

            Row {
                Icon(Icons.Default.Bedtime, "", tint = Color.Blue)
                Text(
                    "${day.totalSleep}/${day.goalSleep} ${stringResource(R.string.hours)}",
                    color = Color.Blue
                )
            }

        }
    }

}

@Preview(showBackground = true)
@Composable
fun StatsPreview() {
    MyHealthTheme {
        //SleepAdd("Stats")
    }
}