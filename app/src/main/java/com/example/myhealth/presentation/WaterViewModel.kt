package com.example.myhealth.presentation
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myhealth.domain.repository.HealthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WaterLogViewModel @Inject constructor(
    private val healthRepo: HealthRepository
) : ViewModel() {

    fun addWater(amountMl: Float) = viewModelScope.launch {
        healthRepo.addWater(amountMl)
    }
}