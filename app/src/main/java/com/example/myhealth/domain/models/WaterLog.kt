package com.example.myhealth.domain.models

data class WaterLog(
    val id: Long = 0,
    val volume: Int,
    val time: Long            // Unix-мс
)
