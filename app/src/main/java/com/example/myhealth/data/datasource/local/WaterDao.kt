package com.example.myhealth.data.datasource.local

import androidx.room.*
import com.example.myhealth.data.datasource.local.entity.WaterEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface WaterDao {

    @Insert suspend fun insert(entity: WaterEntity)

    /** Записи за сегодня (поток). */
    @Query(
        """
           SELECT 
            strftime('%Y-%m-%d', datetime(time/1000, 'unixepoch', 'localtime')) as day,
            SUM(volume) as totalMl
        FROM water_intake
        WHERE time >= :from
        GROUP BY day
        ORDER BY day DESC
        LIMIT 7
        """
    )
    fun getLast7Days(from: Long): Flow<List<WaterDayStat>>

    /** Последние N дней. */
    @Query(
        """
        SELECT SUM(volume) FROM water_intake
        WHERE date(time/1000, 'unixepoch', 'localtime') = date('now', 'localtime')
        """
    )
    fun getToday(): Flow<Int?>
}
data class WaterDayStat(
    val day: String,
    val totalMl: Int
)