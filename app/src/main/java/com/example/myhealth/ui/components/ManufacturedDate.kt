package com.example.myhealth.ui.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myhealth.Screens.Screen
import com.example.myhealth.models.DiaryViewModel
import com.example.myhealth.ui.theme.MyHealthTheme
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.ZoneOffset
import java.util.Calendar
import java.util.TimeZone

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ManufacturedDate() {
    val dateState = rememberDatePickerState()
    val millisToLocalDate = dateState.selectedDateMillis?.let {
        DateUtils().convertMillisToLocalDate(it)
    }
    val dateToString = millisToLocalDate?.let {
        DateUtils().dateToString(millisToLocalDate)
    } ?: ""
    Column {
        DatePicker(
            /*dateFormatter = DatePickerFormatter(
                selectedDateSkeleton = "EE, dd MMM, yyyy",
            ),*/
            title = {
                Text(text = "Manufactured Date")
            },
            state = dateState,
            showModeToggle = true
        )
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            text = dateToString
        )
    }
}


@RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerWithDialog(
    modifier: Modifier = Modifier,
    model: DiaryViewModel
) {

    val dateState = rememberDatePickerState(selectableDates = object : SelectableDates {
        @RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
        override fun isSelectableDate(utcTimeMillis: Long): Boolean {
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val currDay = Instant.ofEpochMilli(utcTimeMillis).atZone(ZoneId.of("UTC+3"))
                    .toLocalDate()
                currDay.month >= LocalDate.now().minusMonths(1).month &&  currDay <= LocalDate.now() && currDay.year == LocalDate.now().year
            } else {
                val calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC+3"))
                calendar.timeInMillis = utcTimeMillis
                calendar[Calendar.DAY_OF_WEEK] != Calendar.SUNDAY &&
                        calendar[Calendar.DAY_OF_WEEK] != Calendar.SATURDAY
            }
        }

        override fun isSelectableYear(year: Int): Boolean {
            return true
        }
    },
        initialSelectedDateMillis = model.selectedDay.collectAsState().value.dayToMillis(),
        initialDisplayedMonthMillis = model.selectedDay.collectAsState().value.dayToMillis())
    val chooseDate = dateState.selectedDateMillis?.let {//выбранная дата
        DateUtils().convertMillisToLocalDate(it)
    }

        if (Screen.Diary.dialog.value) {
            DatePickerDialog(
                onDismissRequest = { Screen.Diary.dialog.value = false },
                confirmButton = {
                    Button(
                        onClick = {
                            Screen.Diary.dialog.value = false
                            if (chooseDate != null) {
                                val thisMouth = Instant.now().atZone(ZoneId.of("UTC+3")).toLocalDate().month == chooseDate.month
                                if (thisMouth){
                                    model.selected( (chooseDate.dayOfMonth + chooseDate.minusMonths(1).month.length(chooseDate.isLeapYear))-1 )
                                }
                                else model.selected(   chooseDate.dayOfMonth-1 )
                            }
                        }
                    ) {
                        Text(text = "OK")
                    }
                },
                dismissButton = {
                    Button(
                        onClick = { Screen.Diary.dialog.value = false }
                    ) {
                        Text(text = "Cancel")
                    }
                }
            ) {
                DatePicker(
                    state = dateState,
                    showModeToggle = true
                )
            }
        }
    }

//@RequiresApi(Build.VERSION_CODES.O)
//@Preview(showBackground = true,
//    showSystemUi = true)
//@Composable
//fun ManufacturedDatePreview() {
//    DateDialogsSampleTheme {
//        ManufacturedDate()
//    }
//}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true,
    showSystemUi = true)
@Composable
fun ManufacturedDateWithDialogPreview() {
   MyHealthTheme {
       // DatePickerWithDialog()
    }
}