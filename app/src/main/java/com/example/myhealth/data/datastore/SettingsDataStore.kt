package com.example.myhealth.data.datastore

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "settings")

class SettingsDataStore(private val context: Context) {

    private val KEY_ONBOARDING = booleanPreferencesKey("onboarding_passed")

    val onboardingPassed: Flow<Boolean> =
        context.dataStore.data.map { it[KEY_ONBOARDING] == true }

    suspend fun setOnboardingPassed(value: Boolean) {
        context.dataStore.edit { it[KEY_ONBOARDING] = value }
    }
}