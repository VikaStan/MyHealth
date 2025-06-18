package com.example.myhealth.data.repository

import com.example.myhealth.data.datasource.local.MealDao
import com.example.myhealth.data.datasource.local.entity.MealEntity
import com.example.myhealth.presentation.diary.MealType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

/** Модель продукта/блюда. */
data class Food(
    val name: String,
    val calories: Int,
    val protein: Int,
    val fat: Int,
    val carbs: Int
)

/** Состояние дневника питания. */
data class DiaryState(
    val calories: Int = 0,
    val protein: Int = 0,
    val fat: Int = 0,
    val carbs: Int = 0
)

@Singleton
class DiaryRepository @Inject constructor(
    private val mealDao: MealDao
) {

    /** Актуальное состояние дневника за сегодня. */
    val diaryState: Flow<DiaryState> =
        combine(
            mealDao.getTodayCalories(),
            mealDao.getTodayProteins(),
            mealDao.getTodayFats(),
            mealDao.getTodayCarbs(),
        ) { calories, proteins, fats, carbs ->
            DiaryState(
                calories = calories ?: 0,
                protein = proteins ?: 0,
                fat = fats ?: 0,
                carbs = carbs ?: 0,
            )
        }

    suspend fun addFood(type: MealType, food: Food) {
        mealDao.insert(
            MealEntity(
                name = food.name,
                calories = food.calories,
                proteins = food.protein,
                fats = food.fat,
                carbs = food.carbs,
                type = type.value,
                time = System.currentTimeMillis()
            )
        )
    }

    /** Список приёмов пищи за сегодня. */
    fun mealsForToday(): Flow<List<MealEntity>> = mealDao.getMealsForToday()

    /** Суммарные калории за последние 7 дней. */
    fun caloriesForLastWeek(): Flow<List<Int>> {
        val weekAgo = System.currentTimeMillis() - 6 * 24 * 60 * 60 * 1000L
        return mealDao.getLast7DaysCalories(weekAgo).map { stats ->
            stats.sortedBy { it.day }.map { it.totalCalories }
        }
    }
}