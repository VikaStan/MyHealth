package com.example.myhealth.domain.repository

import com.example.myhealth.data.datasource.local.MealDao
import com.example.myhealth.data.datasource.local.entity.MealEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MealTimeRepository @Inject constructor(
    private val mealDao: MealDao // твой DAO для еды
) {
    suspend fun addMeal(meal: MealEntity) = mealDao.insert(meal)

    // Калории за последние 7 дней
    fun getCaloriesForLastWeek(): Flow<List<Int>> {
        val weekAgo = System.currentTimeMillis() - 6 * 24 * 60 * 60 * 1000L
        return mealDao.getLast7DaysCalories(weekAgo).map { stats ->
            // stats: List<MealDayStat>, преобразуем в список totalCalories по дням
            stats.sortedBy { it.day }.map { it.totalCalories }
        }
    }
    // Белки за сегодня
    fun getProteinsToday(): Flow<Int> = mealDao.getTodayProteins().map { it ?: 0 }

    // Жиры за сегодня
    fun getFatsToday(): Flow<Int> = mealDao.getTodayFats().map { it ?: 0 }

    // Углеводы за сегодня
    fun getCarbsToday(): Flow<Int> = mealDao.getTodayCarbs().map { it ?: 0 }

    /** Список приёмов пищи за сегодня. */
    fun getMealsForToday(): Flow<List<MealEntity>> = mealDao.getMealsForToday()

    /** Суммарные калории за сегодня. */
    fun getTodayCalories(): Flow<Int> = mealDao.getTodayCalories().map { it ?: 0 }
}