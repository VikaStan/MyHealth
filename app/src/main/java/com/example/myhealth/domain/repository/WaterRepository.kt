package com.example.myhealth.domain.repository

import com.example.myhealth.data.datasource.local.WaterDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class WaterRepository @Inject constructor(
    private val waterDao: WaterDao
) {
    fun getWaterForLastWeek(): Flow<List<Int>> {
        val weekAgo = System.currentTimeMillis() - 6 * 24 * 60 * 60 * 1000L
        return waterDao.getLast7Days(weekAgo).map { stats ->
            stats.sortedBy { it.day }.map { it.totalMl }
        }
    }
}