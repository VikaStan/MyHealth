package com.example.myhealth.models

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.myhealth.domain.models.SleepTime
import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.Duration
import javax.inject.Inject

@HiltViewModel
class SleepAddViewModel @Inject constructor() : ViewModel() {


    var sleepAddDialog by mutableStateOf(false)
    var sleepEditDialog by mutableStateOf(false)
    var isChange = false

    var sleepList = mutableStateListOf<SleepTime>()
    var editedSleep = mutableStateOf(SleepTime(0, Duration.ZERO, "", false))
    private var editSleepIndex by mutableIntStateOf(0)

    fun getSleepList(model: DiaryScreenViewModel) {
        model.selectedDayOld.value.sleepTimeList.forEach {
            {
                if (!sleepList.contains(it))
                    sleepList.add(it)
            }
        }

        fun sleepAddDialogShow(show: Boolean = true) {
            sleepAddDialog = show
        }

        fun sleepEditDialogShow(show: Boolean = true) {
            sleepEditDialog = show
        }

        fun onAddSleepClick(sleep: SleepTime) {
            sleepAddDialogShow()
        }

        fun onDelSwipe(sleep: SleepTime) {
            sleepList.remove(sleep)
            if (!sleepList.contains(editedSleep.value)) {
                editedSleep.value = SleepTime(0, Duration.ZERO, "", false)
            }
            isChange = true
        }

        fun onEditSwipe(sleep: SleepTime) {
            editSleepIndex = sleepList.indexOf(sleep)
            editedSleep.value = sleep
            sleepEditDialog = true
        }

        fun addSleep(sleep: SleepTime) {
            isChange = true
            sleepList.add(sleep)
            sleepAddDialogShow(false)
        }

        fun editSleep(sleep: SleepTime) {
            isChange = true
            sleepList[editSleepIndex] = sleep
            sleepEditDialogShow(false)
        }

        fun updateSleepList(model: DiaryScreenViewModel) {
            if (isChange) {
                model.selectedDayOld.value.sleepTimeList = sleepList
                model.selectedDayOld.value.updateAllCount()
                isChange = false
            }
            sleepList = mutableStateListOf<SleepTime>()
        }
    }
}