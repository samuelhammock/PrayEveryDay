package com.example.prayeveryday

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

class PrayerRequestViewModel(application: Application) : ViewModel() {
    val requests: LiveData<List<PrayerRequest>>
    val repository: PrayerRequestRepository

    init {
        val prayerRequestDatabase = PrayerRequestDatabase.getInstance(application)
        val prayerRequestDao = prayerRequestDatabase.PrayerRequestDao()
        repository = PrayerRequestRepository(prayerRequestDao)

        requests = repository.requests
    }

    fun insertPrayerRequest(request: PrayerRequest) {
        repository.insertPrayerRequest(request)
    }

    fun deletePrayerRequest(request: PrayerRequest) {
        repository.deletePrayerRequest(request)
    }

    fun getAll() {
        repository.getAll()
    }

    fun getAllFromDate(date: String) {
        repository.getRequestsFromDate(date)
    }
}