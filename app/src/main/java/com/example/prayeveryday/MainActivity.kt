/*
This file is the starting point of the app.  It determines what happens when the app is opened.
Currently it just sets up the NavController and sets the view to the main screen.
 */

package com.example.prayeveryday

import android.app.Application
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.room.Room

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState) // starting point of app
        setContent {
            val owner = LocalViewModelStoreOwner.current
            owner?.let {
                val viewModel: PrayerRequestViewModel = viewModel(
                    it,
                    "PrayerRequestViewModel",
                    PrayerRequestViewModelFactory(
                        LocalContext.current.applicationContext
                                as Application
                    )
                )
                Navigator(viewModel) // begin at main screen
            }
        }
    }
}

@Composable
fun Navigator(viewModel: PrayerRequestViewModel) { // controls navigation throughout app
    val navController = rememberNavController()
    val context = LocalContext.current
    val db = Room.databaseBuilder(context, PrayerRequestDatabase::class.java, "requestDatabase").build() // initialize database
    val dao = db.PrayerRequestDao()

    NavHost(navController = navController, startDestination = Today.route) {
        composable(Today.route) {
            TodayScreen(navController, viewModel)
        }
        composable(Calendar.route) {
            CalendarScreen(navController, viewModel)
        }
        composable(NewPrayerRequest.route) {
            NewPrayerRequestScreen(navController, viewModel)
        }
    }
}

class PrayerRequestViewModelFactory(val application: Application) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return PrayerRequestViewModel(application) as T
    }
}