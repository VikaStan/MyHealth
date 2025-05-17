package com.example.myhealth.data.local

    import androidx.room.Database
    import androidx.room.RoomDatabase
    import com.example.myhealth.data.local.entity.WaterEntity
    import com.example.myhealth.data.local.entity.DailyStatsEntity

    @Database(
        entities = [WaterEntity::class, DailyStatsEntity::class],
        version = 1,
        exportSchema = false
    )
    abstract class AppDatabase : RoomDatabase() {
        abstract fun statsDao(): StatsDao
    }
