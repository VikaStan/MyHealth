@file:Suppress("DEPRECATION")

package com.example.myhealth.data.datasource

import android.content.Context
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.fitness.Fitness
import com.google.android.gms.fitness.data.DataType
import com.google.android.gms.fitness.request.DataReadRequest
import com.google.android.gms.fitness.result.DataReadResponse
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FitnessDataSource @Inject constructor(
    @ApplicationContext private val context: Context,
    private val accountProvider: () -> com.google.android.gms.auth.api.signin.GoogleSignInAccount
) {
    suspend fun getStepsForLastWeek(): List<Pair<String, Int>> {
        val account = GoogleSignIn.getLastSignedInAccount(context) ?: return emptyList()

        // Готовим запрос: шаги по дням за 7 дней
        val end = Calendar.getInstance()
        val start = (end.clone() as Calendar).apply { add(Calendar.DAY_OF_YEAR, -6) }

        val readRequest = DataReadRequest.Builder()
            .aggregate(DataType.TYPE_STEP_COUNT_DELTA)
            .bucketByTime(1, TimeUnit.DAYS)
            .setTimeRange(start.timeInMillis, end.timeInMillis, TimeUnit.MILLISECONDS)
            .build()

        val response: DataReadResponse = Fitness.getHistoryClient(context, account)
            .readData(readRequest)
            .await()

        val stepsByDay = mutableListOf<Pair<String, Int>>()
        for (bucket in response.buckets) {
            val dataSet = bucket.getDataSet(DataType.AGGREGATE_STEP_COUNT_DELTA)
            val count = dataSet.dataPoints.sumOf { it.getValue(DataType.AGGREGATE_STEP_COUNT_DELTA.fields[0]).asInt() }
            val day = bucket.getStartTime(TimeUnit.MILLISECONDS)
            // Форматируем дату: yyyy-MM-dd
            val dateStr = java.text.SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date(day))
            stepsByDay.add(dateStr to count)
        }
        return stepsByDay
    }
}