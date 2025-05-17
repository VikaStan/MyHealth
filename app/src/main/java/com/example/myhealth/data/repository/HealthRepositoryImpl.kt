package com.example.myhealth.data.repository

import com.example.myhealth.data.datasource.FitnessDataSource
import com.example.myhealth.data.datasource.LocalDataSource
import com.example.myhealth.domain.models.Stats
import com.example.myhealth.domain.repository.HealthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HealthRepositoryImpl @Inject constructor(
    private val local: LocalDataSource,
    private val fit: FitnessDataSource
) : HealthRepository {

    override fun getTodayStats(): Flow<Stats> =
        combine(local.todayStatsFlow(), kotlinx.coroutines.flow.flow { emit(fit.todaySteps()) }) { localStats, steps ->
            localStats.copy(steps = steps)
        }

    override suspend fun addWater(amountMl: Float) =
        local.addWater(amountMl)
}