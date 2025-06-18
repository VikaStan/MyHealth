package com.example.myhealth.data.repository

import com.example.myhealth.presentation.diary.MealType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
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
class DiaryRepository @Inject constructor() {
    private val _diaryState = MutableStateFlow(DiaryState())
    val diaryState: StateFlow<DiaryState> = _diaryState

    suspend fun addFood(type: MealType, food: Food) {
        _diaryState.value = _diaryState.value.copy(
            calories = _diaryState.value.calories + food.calories,
            protein = _diaryState.value.protein + food.protein,
            fat = _diaryState.value.fat + food.fat,
            carbs = _diaryState.value.carbs + food.carbs
        )
    }
}