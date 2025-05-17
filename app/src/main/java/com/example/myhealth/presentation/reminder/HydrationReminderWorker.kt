package com.example.myhealth.presentation.reminder

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.hilt.work.HiltWorker
import androidx.work.*
import com.example.myhealth.domain.repository.HealthRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.first
import java.util.concurrent.TimeUnit

@HiltWorker
class HydrationReminderWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted params: WorkerParameters,
    private val repo: HealthRepository
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        val stats = repo.getTodayStats().first()   // <-- import first
        /* TODO заменить "60" на prefs.weightKg */
        val norm = 60 * 35
        if (stats.water < norm - 100) {
            sendNotification(norm - stats.water)
        }
        return Result.success()
    }

    private fun sendNotification(missing: Float) {
        val mgr = applicationContext.getSystemService(
            Context.NOTIFICATION_SERVICE
        ) as NotificationManager

        val channelId = "water_channel"
        if (mgr.getNotificationChannel(channelId) == null) {
            mgr.createNotificationChannel(
                NotificationChannel(
                    channelId,
                    "Water",
                    NotificationManager.IMPORTANCE_DEFAULT
                )
            )
        }

        val notif = NotificationCompat.Builder(applicationContext, channelId)
            .setSmallIcon(res.drawable.ic_water)   // замени на ic_water позже
            .setContentTitle("Кажется, вы пили мало воды")
            .setContentText("До нормы осталось ${missing.toInt()} мл")
            .build()

        mgr.notify(1, notif)
    }

    companion object {
        const val WORK_NAME = "HydrationReminder"

        fun schedule(context: Context) {
            val req = PeriodicWorkRequestBuilder<HydrationReminderWorker>(
                1, TimeUnit.HOURS
            )
                .setFlexIntervalDuration(java.time.Duration.ofMinutes(15))
                .build()

            WorkManager.getInstance(context)
                .enqueueUniquePeriodicWork(
                    WORK_NAME,
                    ExistingPeriodicWorkPolicy.KEEP,
                    req
                )
        }
    }
}