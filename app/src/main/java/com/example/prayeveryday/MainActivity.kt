/*
This file is the starting point of the app.  It determines what happens when the app is opened.
Currently it just sets up the NavController and sets the view to the main screen.
 */

package com.example.prayeveryday

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.room.Room

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState) // starting point of app
        setContent {
            Navigator(this) // begin at main screen
        }
    }
}

@Composable
fun Navigator(context: Context) { // controls navigation throughout app
    val navController = rememberNavController()
    val db = Room.databaseBuilder(context, AppDatabase::class.java, "requestDatabase").build() // initialize database
    val dao = db.PrayerRequestDao()

    NavHost(navController = navController, startDestination = Today.route) {
        composable(Today.route) {
            TodayScreen(navController, dao)
        }
        composable(Calendar.route) {
            CalendarScreen(navController, dao)
        }
        composable(NewPrayerRequest.route) {
            NewPrayerRequestScreen(navController, dao)
        }
    }
}