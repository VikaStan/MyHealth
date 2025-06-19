package com.example.myhealth.presentation.statistics

import androidx.lifecycle.ViewModel
import com.example.myhealth.data.repository.StatsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class StatsViewModel @Inject constructor(
    private val repo: StatsRepository
) : ViewModel() {
    val uiState: StateFlow<StatisticsState> = repo.statsState
    fun switchTab(tab: StatsTab) = repo.setCurrentTab(tab)
}
