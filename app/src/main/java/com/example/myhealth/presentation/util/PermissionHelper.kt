package com.example.myhealth.presentation.util

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PermissionHelper @Inject constructor(
    @ApplicationContext val context: Context
) {
    fun requestActivityRecognition(): Boolean {
        val granted = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACTIVITY_RECOGNITION
        ) == PackageManager.PERMISSION_GRANTED
        // для дипломного прототипа имитируем запрос (UI-launcher можно добавить позже)
        return granted || run {
            // TODO: реализовать requestPermissionLauncher
            false
        }
    }
}
