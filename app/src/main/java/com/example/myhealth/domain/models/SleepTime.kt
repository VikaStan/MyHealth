package com.example.myhealth.domain.models

import java.time.Duration

class SleepTime(
    val id: Int,
    var duration: Duration = Duration.ofMinutes(0),
    var description: String,
    var isAlarmed: Boolean
) {
}