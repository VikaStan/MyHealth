package com.example.myhealth.data.repository

import com.example.myhealth.data.datasource.FitnessDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class StepsRepository(
    private val fitnessDataSource: FitnessDataSource
) {
    fun getStepsForLastWeek(): Flow<List<Int>> = flow {
        val stepsList = fitnessDataSource.getStepsForLastWeek()
        // Возвращаем только значения (по порядку дат)
        emit(stepsList.map { it.second })
    }
}