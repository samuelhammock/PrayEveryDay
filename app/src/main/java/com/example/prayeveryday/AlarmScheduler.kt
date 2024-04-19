package com.example.prayeveryday

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import java.time.Duration
import java.time.LocalTime
import kotlin.random.Random

class AlarmScheduler(private val context: Context) {

    private val alarmManager: AlarmManager = context.getSystemService(AlarmManager::class.java)
    fun schedule(time: LocalTime) {
        val intent = Intent(context, AlarmReceiver::class.java).apply {
            putExtra("ALARM_EXTRA", "test")
        }

        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP,
            time.toNanoOfDay(),
            Duration.ofDays(1).toMillis(),
            PendingIntent.getBroadcast(
                context,
                Random.nextInt(),
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
        )
    }

    fun cancel() {
        alarmManager.cancel(
            PendingIntent.getBroadcast(
                context,
                Random.nextInt(),
                Intent(context, AlarmReceiver::class.java),
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
        )
    }
}