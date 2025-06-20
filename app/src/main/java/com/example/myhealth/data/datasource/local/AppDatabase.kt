package com.example.myhealth.data.datasource.local

    import androidx.room.Database
    import androidx.room.RoomDatabase
    import com.example.myhealth.data.datasource.local.entity.DailyStatsEntity
    import com.example.myhealth.data.datasource.local.entity.MealEntity
    import com.example.myhealth.data.datasource.local.entity.ProductEntity
    import com.example.myhealth.data.datasource.local.entity.WaterEntity

@Database(
    entities = [WaterEntity::class, DailyStatsEntity::class, MealEntity::class, ProductEntity::class],
    version = 2,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun statsDao(): StatsDao
    abstract fun waterDao(): WaterDao
    abstract fun mealDao(): MealDao
    abstract fun productDao(): ProductDao
}