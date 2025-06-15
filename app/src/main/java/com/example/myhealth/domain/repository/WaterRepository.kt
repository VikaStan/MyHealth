package com.example.myhealth.domain.repository

import com.example.myhealth.data.datasource.local.WaterDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import com.example.myhealth.data.datasource.local.entity.WaterEntity
import com.example.myhealth.domain.models.WaterLog
import java.time.LocalDate
import java.time.ZoneId

open class WaterRepository @Inject constructor(
    private val waterDao: WaterDao
) {
    open suspend fun addWater(volume: Int) {
        waterDao.insert(
            WaterEntity(volume = volume.toFloat(), time = System.currentTimeMillis())
        )
    }

    /** Суммарно выпито воды за сегодня (мл). */
    open fun waterToday(): Flow<Int> =
        waterDao.getToday().map { it ?: 0 }

    /** Список записей за последние [days] дней. */
    open fun waterLogs(days: Int): Flow<List<WaterLog>> {
        val from = System.currentTimeMillis() - (days - 1) * 24 * 60 * 60 * 1000L
        return waterDao.getLast7Days(from).map { list ->
            list.map { stat ->
                val dateMillis = LocalDate.parse(stat.day)
                    .atStartOfDay(ZoneId.systemDefault())
                    .toInstant()
                    .toEpochMilli()
                WaterLog(volume = stat.totalMl, time = dateMillis)
            }
        }
    }

    fun getWaterForLastWeek(): Flow<List<Int>> {
        val weekAgo = System.currentTimeMillis() - 6 * 24 * 60 * 60 * 1000L
        return waterDao.getLast7Days(weekAgo).map { stats ->
            stats.sortedBy { it.day }.map { it.totalMl }
        }
    }
}