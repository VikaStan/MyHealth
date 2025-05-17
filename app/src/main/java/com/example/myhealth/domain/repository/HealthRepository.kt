package com.example.myhealth.domain.repository

import com.example.myhealth.domain.models.Stats
import kotlinx.coroutines.flow.Flow

interface HealthRepository {
    fun getTodaysStats(): Flow<Stats>
    suspend fun addWater(amountML: Float)
}