package com.example.myhealth.presentation.account

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myhealth.data.repository.ProfileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AccountViewModel @Inject constructor(
    private val repo: ProfileRepository
) : ViewModel() {

    var uiState by mutableStateOf(ProfileState())
        private set

    init {
        loadProfile()
    }

    private fun loadProfile() = viewModelScope.launch {
        uiState = repo.getProfile()
    }

    fun onNameChange(value: String) {
        uiState = uiState.copy(name = value)
    }

    fun onAgeChange(value: String) {
        uiState = uiState.copy(age = value.toIntOrNull() ?: 0)
    }

    fun onHeightChange(value: String) {
        uiState = uiState.copy(height = value.toIntOrNull() ?: 0)
    }

    fun onWeightChange(value: String) {
        uiState = uiState.copy(weight = value.toIntOrNull() ?: 0)
    }

    private fun recalcTargets(state: ProfileState): ProfileState {
        val waterTarget = state.weight * 30
        val calTarget = (10 * state.weight + 6.25 * state.height - 5 * state.age + 5).toInt()
        return state.copy(waterTarget = waterTarget, caloriesTarget = calTarget)
    }

    fun onSave() = viewModelScope.launch {
        uiState = recalcTargets(uiState)
        repo.saveProfile(uiState)
    }

    fun logout() = viewModelScope.launch { repo.logout() }
    fun deleteAccount() = viewModelScope.launch { repo.deleteProfile() }
}

data class ProfileState(
    val name: String = "",
    val age: Int = 0,
    val height: Int = 0,
    val weight: Int = 0,
    val waterTarget: Int = 0,
    val caloriesTarget: Int = 0
)