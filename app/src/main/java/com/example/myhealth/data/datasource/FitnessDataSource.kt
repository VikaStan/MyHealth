@file:Suppress("DEPRECATION")

package com.example.myhealth.data.datasource

import android.content.Context
import com.google.android.gms.fitness.Fitness
import com.google.android.gms.fitness.data.DataType
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FitnessDataSource @Inject constructor(
    @ApplicationContext private val context: Context,
    private val accountProvider: () -> com.google.android.gms.auth.api.signin.GoogleSignInAccount
) {
    suspend fun todaySteps(): Int {
        val ds = Fitness.getHistoryClient(context, accountProvider())
            .readDailyTotal(DataType.TYPE_STEP_COUNT_DELTA)
            .await()
        return ds.dataPoints
            .firstOrNull()
            ?.getValue(DataType.TYPE_STEP_COUNT_DELTA.fields[0])
            ?.asInt() ?: 0
    }
}