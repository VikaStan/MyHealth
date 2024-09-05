package com.example.myhealth.domain.usecases

import com.example.myhealth.domain.models.Day
import com.example.myhealth.domain.repository.DayRepository
import java.time.LocalDate

class GetDayListUseCase(private val dayRepository: DayRepository) {

    fun execute(startDate: LocalDate, endDate: LocalDate): List<Day> {
        return dayRepository.getDayList(startDate = startDate, endDate = endDate)
    }
}