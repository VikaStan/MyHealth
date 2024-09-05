package com.example.myhealth.domain.usecases

import com.example.myhealth.domain.models.Day
import com.example.myhealth.domain.repository.DayRepository
import java.time.LocalDate

class GetDayUseByDateCase(private val dayRepository: DayRepository) {

    fun execute(date: LocalDate): Day {
        return dayRepository.getDayByDate(date)
    }
}