package com.example.myhealth.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.myhealth.data.DayOld

@Dao
interface DayDao {
    @Query("SELECT * FROM days")
    fun getDays(): List<DayOld>
    @Insert
    fun addDay(dayOld: DayOld)

    @Update
    fun updDay(dayOld: DayOld)
}