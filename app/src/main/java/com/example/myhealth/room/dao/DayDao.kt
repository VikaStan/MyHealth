package com.example.myhealth.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.myhealth.data.datasource.local.entity.DayEntity

@Dao
interface DayDao {
    @Query("SELECT * FROM days")
    fun getDays(): List<DayEntity>
    @Insert
    fun addDay(dayOld: DayEntity)

    @Update
    fun updDay(dayOld: DayEntity)
}