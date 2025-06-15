package com.example.myhealth.data.repository

import com.example.myhealth.data.datasource.local.StatsDao
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
    private val waterDao: WaterDao,
    private val statsDao: StatsDao
) : HealthRepository {

    override fun todayStats(): Flow<Stats> =
        combine(
            statsDao.dailyStatsFlow(),
            waterDao.getToday().map { it?.toFloat() ?: 0f }
        ) { daily, water ->
            Stats(
                steps = daily?.steps ?: 0,
                waterDrunk = water
            )
        }

    override suspend fun addWater(amountML: Float) {
        waterDao.insert(WaterEntity(volume = amountML, time = System.currentTimeMillis()))
    }

}