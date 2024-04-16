/*
This file defines the local database used to store the prayer requests, the data class used to store data,
and the interface used to access the database
 */

package com.example.prayeveryday

import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Delete
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.RoomDatabase
import java.util.Date

@Database(entities = [PrayerRequest::class], version = 1)
abstract class AppDatabase : RoomDatabase() { // defines the database
    abstract fun PrayerRequestDao() : PrayerRequestDao
}

@Entity(tableName = "prayerRequests")
data class PrayerRequest( // defines the data class the database stores
    @PrimaryKey(autoGenerate = true) val uid: Int,
    @ColumnInfo(name = "Label") val label: String,
    @ColumnInfo(name = "Date") val date: Date,
    @ColumnInfo(name = "Summary") val summary: String,
    @ColumnInfo(name = "Details") val details: String,
    @ColumnInfo(name = "Repeating") val repeating: Boolean
    )

@Dao
interface PrayerRequestDao { // defines methods that can be used to interact with the database
    @Insert
    fun insert(prayerRequest: PrayerRequest)

    @Delete
    fun delete(prayerRequest: PrayerRequest)

    @Query("SELECT * FROM prayerRequests")
    fun getAll(): List<PrayerRequest>

    @Query("SELECT * FROM prayerRequests WHERE date = :date")
    fun getRequestsFromDate(date: Date): PrayerRequest
}