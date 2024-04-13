/*
This file is the starting point of the app.  It determines what happens when the app is opened.
Currently it just sets up the NavController and sets the view to the main screen.
 */

package com.example.prayeveryday

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState) // starting point of app
        setContent {
            Navigator() // begin at main screen
        }
    }
}

@Preview
@Composable
fun Navigator() { // controls navigation throughout app
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Today.route) {
        composable(Today.route) {
            TodayScreen(navController)
        }
        composable(Calendar.route) {
            CalendarScreen(navController)
        }
        composable(NewPrayerRequest.route) {
            NewPrayerRequestScreen(navController)
        }
    }
}