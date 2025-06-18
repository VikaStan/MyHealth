package com.example.myhealth

import android.app.Application
import com.example.myhealth.room.PersonRepository
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyHealthApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        PersonRepository.init(PreferencesManager(this))
    }
}