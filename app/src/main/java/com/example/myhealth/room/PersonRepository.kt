package com.example.myhealth.room

import com.example.myhealth.PreferencesManager
/**
 * Repository for user-related data.
 */
object PersonRepository {

    private var prefs: PreferencesManager? = null

    fun init(manager: PreferencesManager) {
        prefs = manager
    }
    /** Возвращает целевое количество калорий пользователя. */
    fun getCaloriesTarget(): Int {
        return prefs?.getData("caloriesGoal", 2200f)?.toInt() ?: 2200
    }
}