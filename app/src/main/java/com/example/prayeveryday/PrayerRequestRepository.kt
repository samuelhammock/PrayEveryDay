package com.example.prayeveryday

import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PrayerRequestRepository(private val prayerRequestDao: PrayerRequestDao) {
    val requests = MutableLiveData<List<PrayerRequest>>()
    private val scope = CoroutineScope(Dispatchers.Main)

    fun insertPrayerRequest(request: PrayerRequest) {
        scope.launch(Dispatchers.IO) {
            prayerRequestDao.insert(request)
        }
    }

    fun deletePrayerRequest(request: PrayerRequest) {
        scope.launch(Dispatchers.IO) {
            prayerRequestDao.delete(request)
        }
    }

    fun getAll() {
        scope.launch(Dispatchers.IO) {
            requests.value = prayerRequestDao.getAll()
        }
    }

    fun getRequestsFromDate(date: String) {
        scope.launch(Dispatchers.IO) {
            requests.value = prayerRequestDao.getRequestsFromDate(date)
        }
    }
}