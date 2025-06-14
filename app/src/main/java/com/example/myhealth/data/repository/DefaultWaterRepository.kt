package com.example.myhealth.data.repository

import com.example.myhealth.data.datasource.local.WaterDao
import com.example.myhealth.data.datasource.local.entity.WaterEntity
import com.example.myhealth.data.datasource.local.toDomain
import com.example.myhealth.di.IoDispatcher
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class DefaultWaterRepository @Inject constructor(
    private val dao: WaterDao,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : WaterRepository {

    /** Добавляет объём [volume] (мл) в текущий день. */
    override suspend fun addWater(volume: Int) = withContext(dispatcher) {
        dao.insert(WaterEntity(volume = volume, time = System.currentTimeMillis()))
    }

    /** Суммарно выпито воды за сегодня (мл) — поток, обновляется автоматически. */
    override fun waterToday(): Flow<Int> =
        dao.observeToday()
            .map { list -> list.sumOf { it.volume } }

    /** Список записей за последние N дней (для журнала). */
    override fun waterLogs(days: Int): Flow<List<WaterLog>> =
        dao.observeLastDays(days).map { list -> list.map { it.toDomain() } }
}