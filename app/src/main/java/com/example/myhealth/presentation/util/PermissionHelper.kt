package com.example.myhealth.presentation.util

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.ComponentActivity
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.resume

@Singleton
class PermissionHelper @Inject constructor(
    @ApplicationContext val context: Context
) {
    @RequiresApi(Build.VERSION_CODES.Q)
    suspend fun requestActivityRecognition(caller: ComponentActivity): Boolean {
        val granted = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACTIVITY_RECOGNITION
        ) == PackageManager.PERMISSION_GRANTED
        if (granted) return true

        return suspendCancellableCoroutine { cont ->
            val launcher = caller.registerForActivityResult(
                ActivityResultContracts.RequestPermission()
            ) { result: Boolean ->
                cont.resume(result)
            }
            launcher.launch(Manifest.permission.ACTIVITY_RECOGNITION)
        }
    }
}
