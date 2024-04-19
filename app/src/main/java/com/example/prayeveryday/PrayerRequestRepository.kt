package com.example.prayeveryday

import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.time.LocalDate

class PrayerRequestRepository(private val prayerRequestDao: PrayerRequestDao) {
    val requests = MutableLiveData<List<PrayerRequest>>()
    private val scope = CoroutineScope(Dispatchers.Main)

    fun insertPrayerRequest(request: PrayerRequest) {
        scope.launch(Dispatchers.IO) {
            prayerRequestDao.insert(request)
        }
    }

    fun insertPrayerRequests(requests: List<PrayerRequest>) {
        scope.launch(Dispatchers.IO) {
            prayerRequestDao.insertAll(requests)
        }
    }

    fun deletePrayerRequest(request: PrayerRequest) {
        scope.launch(Dispatchers.IO) {
            prayerRequestDao.delete(request)
        }
    }

    fun getAll() {
        scope.launch(Dispatchers.IO) {
            requests.postValue(asyncGetAll().await())
        }
    }

    private fun asyncGetAll(): Deferred<List<PrayerRequest>?> =
        scope.async(Dispatchers.IO) {
            return@async prayerRequestDao.getAll()
        }

    fun getRequestsFromDate(date: LocalDate) {
        scope.launch(Dispatchers.IO) {
            requests.postValue(asyncGetRequestsFromDate(date).await())
        }
    }

    private fun asyncGetRequestsFromDate(date: LocalDate): Deferred<List<PrayerRequest>?> =
        scope.async(Dispatchers.IO) {
            return@async prayerRequestDao.getRequestsFromDate(date.toString())
        }
}