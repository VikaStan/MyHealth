package com.example.myhealth.data.repository

import com.example.myhealth.data.datasource.FitnessDataSource
import com.example.myhealth.data.datasource.LocalDataSource
import com.example.myhealth.data.datasource.local.WaterDao
import com.example.myhealth.data.datasource.local.entity.WaterEntity
import com.example.myhealth.domain.models.Stats
import com.example.myhealth.domain.repository.HealthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HealthRepositoryImpl @Inject constructor(
    private val local: LocalDataSource,
    private val fit: FitnessDataSource,
    private val waterDao: WaterDao,
    private val caloriesDao:CaloriesDao
) : HealthRepository {

    override fun getTodayStats(): Flow<Stats> =
        combine(local.todayStatsFlow(), kotlinx.coroutines.flow.flow { emit(fit.todaySteps()) }) { localStats, steps ->
            localStats.copy(steps = steps)
        }

    override suspend fun addWater(amountMl: Float) =
        local.addWater(amountMl)

    override fun todayStats(): Flow<Stats> =
        combine(
            waterDao.observeToday()
                .map { list -> list.sumOf { it.volume } },
            caloriesDao.caloriesToday()
        ) { water, kcal -> Stats(water, kcal) }

    override suspend fun addWater(amountML: Float) {
        waterDao.insert(WaterEntity(volume = amountML, time = System.currentTimeMillis()))
    }
}