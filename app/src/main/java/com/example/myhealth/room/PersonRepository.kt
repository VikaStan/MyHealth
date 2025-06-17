package com.example.myhealth.room

/**
 * Repository for user-related data.
 */
object PersonRepository {
    /** Возвращает целевое количество калорий пользователя. */
    fun getCaloriesTarget(): Int {
        // TODO: получить значение из БД или настроек
        return 2200
    }
}