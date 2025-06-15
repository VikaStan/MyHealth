package com.example.myhealth.data.repository

import com.example.myhealth.data.datasource.local.WaterDao
import com.example.myhealth.di.IoDispatcher
import com.example.myhealth.domain.repository.WaterRepository
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class DefaultWaterRepository @Inject constructor(
    private val dao: WaterDao,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : WaterRepository(dao) {

    /** Добавляет объём [volume] (мл) в текущий день. */
    override suspend fun addWater(volume: Int) = withContext(dispatcher) {
        super.addWater(volume)
    }
}