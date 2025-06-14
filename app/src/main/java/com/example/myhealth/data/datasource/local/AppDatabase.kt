package com.example.myhealth.data.datasource.local

    import androidx.room.Database
    import androidx.room.RoomDatabase
    import com.example.myhealth.data.datasource.local.entity.WaterEntity
    import com.example.myhealth.data.datasource.local.entity.DailyStatsEntity

    @Database(
        entities = [WaterEntity::class, DailyStatsEntity::class],
        version = 1,
        exportSchema = false
    )
    abstract class AppDatabase : RoomDatabase() {
        abstract fun statsDao(): StatsDao
        abstract fun waterDao(): WaterDao
    }
