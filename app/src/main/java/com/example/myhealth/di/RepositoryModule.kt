package com.example.myhealth.di

import com.example.myhealth.data.repository.DefaultWaterRepository
import com.example.myhealth.data.repository.HealthRepositoryImpl
import com.example.myhealth.domain.repository.HealthRepository
import com.example.myhealth.domain.repository.WaterRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    @Singleton
    fun bindHealthRepository(impl: HealthRepositoryImpl): HealthRepository

    @Binds
    @Singleton
    fun bindWaterRepository(impl: DefaultWaterRepository): WaterRepository
}