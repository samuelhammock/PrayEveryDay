package com.example.prayeveryday

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import java.time.LocalDate

class PrayerRequestViewModel(application: Application) : ViewModel() {
    val requests: LiveData<List<PrayerRequest>>
    private val repository: PrayerRequestRepository

    init {
        val prayerRequestDatabase = PrayerRequestDatabase.getInstance(application)
        val prayerRequestDao = prayerRequestDatabase.PrayerRequestDao()
        repository = PrayerRequestRepository(prayerRequestDao)

        requests = repository.requests
    }

    fun insertPrayerRequest(request: PrayerRequest) {
        repository.insertPrayerRequest(request)
    }

    fun insertPrayerRequests(requests: List<PrayerRequest>) {
        repository.insertPrayerRequests(requests)
    }

    fun deletePrayerRequest(request: PrayerRequest) {
        repository.deletePrayerRequest(request)
    }

    fun getAll() {
        repository.getAll()
    }

    fun getAllFromDate(date: LocalDate) {
        repository.getRequestsFromDate(date)
    }
}