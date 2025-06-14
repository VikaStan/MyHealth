package com.example.myhealth.presentation.reminder

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import com.example.myhealth.R
import com.example.myhealth.domain.repository.HealthRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.first
import java.util.concurrent.TimeUnit

private const val WORK_NAME   = "WaterReminder"
private const val CHANNEL_ID  = "WATER_CHANNEL"
private const val NOTIF_ID    = 101
private const val STEP_MINML  = 100          // уведомляем, если осталось ≥ 100 мл
private const val PLUS_AMOUNT = 200        // размер «стакана»
private const val DAILY_NORM_ML = 2000

@HiltWorker
class HydrationReminderWorker @AssistedInject constructor(
    @Assisted ctx: Context,
    @Assisted params: WorkerParameters,
    private val healthRepo: HealthRepository
) : CoroutineWorker(ctx, params) {

    override suspend fun doWork(): Result {
        createChannel()

        // Берём сегодняшнюю статистику
        val stats = healthRepo.todayStats().first()
        val remain = (DAILY_NORM_ML - stats.water.toInt()).coerceAtLeast(0)

        if (remain >= STEP_MINML) {
            sendNotification(remain)
        }
        return Result.success()
    }

    private fun sendNotification(remain: Int) {
        val text = applicationContext.getString(
            R.string.water_reminder_text,
            remain
        )

        val notif = NotificationCompat.Builder(applicationContext, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_water)          // ИКОНКА ЕСТЬ в res/drawable
            .setContentTitle(applicationContext.getString(R.string.app_name))
            .setContentText(text)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)
            .build()

        NotificationManagerCompat
            .from(applicationContext)
            .notify(NOTIF_ID, notif)
    }

    private fun createChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                applicationContext.getString(R.string.channel_water),
                NotificationManager.IMPORTANCE_DEFAULT
            )
            applicationContext
                .getSystemService(NotificationManager::class.java)
                .createNotificationChannel(channel)
        }
    }

    companion object {
        /** Регистрирует (или обновляет) воркер раз в 45 минут. */
        fun schedule(context: Context) {
            val request = PeriodicWorkRequestBuilder<HydrationReminderWorker>(
                45, TimeUnit.MINUTES
            )
                .setFlexIntervalDuration(15, TimeUnit.MINUTES)   // NEW API
                .build()

            WorkManager.getInstance(context).enqueueUniquePeriodicWork(
                WORK_NAME,
                ExistingPeriodicWorkPolicy.UPDATE,               // не плодим дубликаты
                request
            )
        }
    }
}