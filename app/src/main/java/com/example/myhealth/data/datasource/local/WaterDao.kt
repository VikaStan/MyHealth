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
        SELECT * FROM water_intake       
        WHERE date(time / 1000, 'unixepoch', 'localtime') = date('now','localtime')
        ORDER BY time DESC
        """
    )
    fun observeToday(): Flow<List<WaterEntity>>

    /** Последние N дней. */
    @Query(
        """
        SELECT * FROM water_intake
        WHERE time >= strftime('%s','now','localtime','-|| :days ||day')*1000
        ORDER BY time DESC
        """
    )
    fun observeLastDays(days: Int): Flow<List<WaterEntity>>
}