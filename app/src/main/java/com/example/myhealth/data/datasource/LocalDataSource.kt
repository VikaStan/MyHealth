package com.example.myhealth.data.datasource

import com.example.myhealth.data.datasource.local.StatsDao
import com.example.myhealth.data.datasource.local.entity.WaterEntity
import com.example.myhealth.domain.models.Stats
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalDataSource @Inject constructor(
    private val dao: StatsDao
) {

    fun todayStatsFlow(): Flow<Stats> =
        combine(
            dao.dailyStatsFlow(),
            dao.waterTodayFlow()
        ) { daily, water ->
            Stats(
                steps = daily?.steps ?: 0,
                waterDrunk = water ?: 0f
            )
        }

    suspend fun addWater(amountMl: Float) =
        dao.insertWater(WaterEntity(volume = amountMl))
}