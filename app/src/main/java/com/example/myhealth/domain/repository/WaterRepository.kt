package com.example.myhealth.domain.repository

import com.example.myhealth.domain.models.WaterLog
import kotlinx.coroutines.flow.Flow

interface WaterRepository {
    suspend fun addWater(volume: Int)
    fun waterToday(): Flow<Int>
    fun waterLogs(days: Int): Flow<List<WaterLog>>
}