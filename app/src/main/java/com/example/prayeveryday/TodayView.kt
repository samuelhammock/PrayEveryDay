/*
This file creates the today view using the components defined in TodayComponents.kt.
It assembles the view using those components and the app scaffold.
 */

package com.example.prayeveryday

import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.navigation.NavHostController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodayScreen(navController: NavHostController, viewModel: PrayerRequestViewModel) { // initializes today screen
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = { DisplayTopBar(scrollBehavior, "Today's Prayer Requests", drawerState) },
        content = { padding ->  SideDrawer(drawerState, padding, Page.TODAY, viewModel, navController)},
        bottomBar = { DisplayNavBar(navController) }
    )
}

