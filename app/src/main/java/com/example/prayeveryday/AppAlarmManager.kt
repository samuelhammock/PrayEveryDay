package com.example.prayeveryday

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context

class AppAlarmManager : Application() {
    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
    }

    private fun createNotificationChannel() {
        val channelName = "PrayEveryDay"
        val channelDescription = "PrayEveryDay Channel"
        val importance = NotificationManager.IMPORTANCE_HIGH
        val channel = NotificationChannel("PrayEveryDay", channelName, importance).apply {
            description = channelDescription
        }
        val notificationManager: NotificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }
}