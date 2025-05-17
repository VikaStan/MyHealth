package com.example.myhealth.di

import android.content.Context
import androidx.room.Room
import com.example.myhealth.data.local.AppDatabase
import com.example.myhealth.data.local.StatsDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): AppDatabase =
        Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "myhealth.db"
        )
            // для прототипа допускаю destructiveMigration;
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    fun provideStatsDao(db: AppDatabase): StatsDao = db.statsDao()
}