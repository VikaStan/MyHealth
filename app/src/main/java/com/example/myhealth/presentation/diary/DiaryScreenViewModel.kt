package com.example.myhealth.presentation.diary

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myhealth.data.repository.DiaryRepository
import com.example.myhealth.data.repository.Food
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DiaryScreenViewModel @Inject constructor(
    private val repo: DiaryRepository
) : ViewModel() {

    val uiState = repo.diaryState

    /** Добавляем приём пищи */
    fun addFood(type: MealType, food: Food) {
        viewModelScope.launch { repo.addFood(type, food) }
    }
}