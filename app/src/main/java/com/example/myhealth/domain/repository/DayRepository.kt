package com.example.myhealth.domain.repository

import com.example.myhealth.domain.models.Day
import java.time.LocalDate

interface DayRepository {
    fun getDayList(startDate: LocalDate, endDate: LocalDate): List<Day>
    fun getDayByDate(date:LocalDate): Day
    fun saveDayByDate(date:LocalDate)
    fun updateDayByDate(updatedDay:Day)
}