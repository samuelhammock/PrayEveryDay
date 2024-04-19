/*
This file defines the local database used to store the prayer requests, the data class used to store data,
and the interface used to access the database
 */

package com.example.prayeveryday

import android.content.Context
import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Delete
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [PrayerRequest::class], version = 1)
abstract class PrayerRequestDatabase : RoomDatabase() { // defines the database
    abstract fun PrayerRequestDao() : PrayerRequestDao

    companion object {
        private var INSTANCE: PrayerRequestDatabase? = null

        fun getInstance(context: Context): PrayerRequestDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        PrayerRequestDatabase::class.java,
                        "prayerRequestDatabase"
                    ).fallbackToDestructiveMigration()
                        .build()

                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}

@Entity(tableName = "prayerRequests")
data class PrayerRequest( // defines the data class the database stores
    @PrimaryKey(autoGenerate = true)
    var uid: Int,

    @ColumnInfo(name = "Label")
    var label: String,

    @ColumnInfo(name = "Date")
    var date: String,

    @ColumnInfo(name = "Summary")
    var summary: String,

    @ColumnInfo(name = "Details")
    var details: String,

    @ColumnInfo(name = "Repeating")
    var repeating: Boolean
    )

@Dao
interface PrayerRequestDao { // defines methods that can be used to interact with the database
    @Insert
    fun insert(prayerRequest: PrayerRequest)

    @Insert
    fun insertAll(prayerRequests: List<PrayerRequest>)

    @Delete
    fun delete(prayerRequest: PrayerRequest)

    @Query("SELECT * FROM prayerRequests")
    fun getAll(): List<PrayerRequest>

    @Query("SELECT * FROM prayerRequests WHERE date = :date")
    fun getRequestsFromDate(date: String): List<PrayerRequest>
}