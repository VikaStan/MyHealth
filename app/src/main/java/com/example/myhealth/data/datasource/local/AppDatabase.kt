package com.example.myhealth.data.datasource.local

    import androidx.room.Database
    import androidx.room.RoomDatabase
    import com.example.myhealth.data.datasource.local.entity.WaterEntity
    import com.example.myhealth.data.datasource.local.entity.DailyStatsEntity
    import com.example.myhealth.data.datasource.local.entity.MealEntity

@Database(
    entities = [WaterEntity::class, DailyStatsEntity::class, MealEntity::class],
    version = 2,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun statsDao(): StatsDao
    abstract fun waterDao(): WaterDao
    abstract fun mealDao(): MealDao
}