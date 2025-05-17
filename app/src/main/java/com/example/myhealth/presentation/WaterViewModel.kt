package com.example.myhealth.presentation
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myhealth.domain.models.Stats
import com.example.myhealth.domain.repository.HealthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WaterLogViewModel @Inject constructor(
    private val repository: HealthRepository
) : ViewModel() {

    fun addWater(amountMl: Float) = viewModelScope.launch {
        repository.addWater(amountMl)
    }
}