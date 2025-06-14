package com.example.myhealth.data.datasource.local

import androidx.room.*
import com.example.myhealth.data.datasource.local.entity.DailyStatsEntity
import com.example.myhealth.data.datasource.local.entity.WaterEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface StatsDao {

    /* ---------- Water ---------- */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertWater(entry: WaterEntity)

    @Query("""
        SELECT SUM(volume)
        FROM water_intake
        WHERE date(time/1000,'unixepoch','localtime') = date('now','localtime')
    """)
    fun waterTodayFlow(): Flow<Float?>

    /* ---------- Daily stats (steps, calories) ---------- */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertDailyStats(stats: DailyStatsEntity)

    @Query("SELECT * FROM daily_stats WHERE date = date('now','localtime')")
    fun dailyStatsFlow(): Flow<DailyStatsEntity?>

    @Query("SELECT calories FROM daily_stats WHERE date = date('now','localtime')")
    fun caloriesToday(): Flow<Int?>
}