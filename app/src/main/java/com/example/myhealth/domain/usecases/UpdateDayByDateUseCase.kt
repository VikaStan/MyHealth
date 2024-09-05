package com.example.myhealth.domain.usecases

import com.example.myhealth.domain.models.Day
import com.example.myhealth.domain.repository.DayRepository

class UpdateDayByDateUseCase(private val dayRepository: DayRepository) {

    fun execute(updatedDay: Day) {
        dayRepository.updateDayByDate(updatedDay = updatedDay)
    }
}