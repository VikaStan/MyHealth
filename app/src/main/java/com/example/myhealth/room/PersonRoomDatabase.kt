package com.example.myhealth.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.myhealth.room.Converters.DateConverter
import com.example.myhealth.room.dao.DayDao
import com.example.myhealth.room.dao.PersonDao
import com.example.myhealth.data.Day
import com.example.myhealth.data.Person

@Database(entities = [(Person::class),(Day::class)], version = 1)
@TypeConverters(DateConverter::class)
abstract class PersonRoomDatabase: RoomDatabase() {
    abstract fun personDao(): PersonDao
    abstract fun dayDao(): DayDao

    // реализуем синглтон
    companion object {
        private var INSTANCE: PersonRoomDatabase? = null
        fun getInstance(context: Context): PersonRoomDatabase {

            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        PersonRoomDatabase::class.java,
                        "usersdb"
                    ).fallbackToDestructiveMigration().build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}