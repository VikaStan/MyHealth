package com.example.myhealth.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.myhealth.data.Person
import com.example.myhealth.data.datasource.local.entity.DayEntity
import com.example.myhealth.room.converters.DateConverter
import com.example.myhealth.room.converters.MealTimeListConverter
import com.example.myhealth.room.converters.ProductListConverter
import com.example.myhealth.room.dao.DayDao
import com.example.myhealth.room.dao.PersonDao

@Database(
    entities = [(Person::class), (DayEntity::class)],
    version = 2,
    exportSchema = false
)
@TypeConverters(
    DateConverter::class,
    ProductListConverter::class,
    MealTimeListConverter::class
)
abstract class HealthRoomDb : RoomDatabase() {
    abstract val personDao: PersonDao
    abstract val dayDao: DayDao

    // реализуем синглтон
   /* companion object {
        private var INSTANCE: HealthRoomDb? = null
        fun getInstance(context: Context): HealthRoomDb {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        HealthRoomDb::class.java,
                        "usersdb"
                    ).fallbackToDestructiveMigration().build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }*/
}