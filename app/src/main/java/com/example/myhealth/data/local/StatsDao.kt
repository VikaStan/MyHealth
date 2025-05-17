package com.example.myhealth.data.local

import androidx.room.*
import com.example.myhealth.data.local.entity.DailyStatsEntity
import com.example.myhealth.data.local.entity.WaterEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface StatsDao {

    /* ---------- Water ---------- */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertWater(entry: WaterEntity)

    @Query("""
        SELECT SUM(amountMl) 
        FROM water_intake 
        WHERE date(timestamp/1000,'unixepoch','localtime') = date('now','localtime')
    """)
    fun waterTodayFlow(): Flow<Float?>

    /* ---------- Daily stats (steps, calories) ---------- */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertDailyStats(stats: DailyStatsEntity)

    @Query("SELECT * FROM daily_stats WHERE date = date('now','localtime')")
    fun dailyStatsFlow(): Flow<DailyStatsEntity?>
}