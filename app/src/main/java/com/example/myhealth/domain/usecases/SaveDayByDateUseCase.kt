package com.example.myhealth.domain.usecases

import com.example.myhealth.domain.repository.DayRepository
import java.time.LocalDate

class SaveDayByDateUseCase(private val dayRepository: DayRepository) {

    fun execute(date: LocalDate) {
        dayRepository.saveDayByDate(date = date)
    }
}