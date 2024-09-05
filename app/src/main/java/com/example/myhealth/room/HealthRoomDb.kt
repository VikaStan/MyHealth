package com.example.myhealth.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.myhealth.room.Converters.DateConverter
import com.example.myhealth.room.dao.DayDao
import com.example.myhealth.room.dao.PersonDao
import com.example.myhealth.data.DayOld
import com.example.myhealth.data.Person

@Database(
    entities = [(Person::class), (DayOld::class)],
    version = 1,
    exportSchema = false
)
@TypeConverters(DateConverter::class)
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