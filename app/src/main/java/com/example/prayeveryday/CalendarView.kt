/*
This file creates the calendar view using the components defined in CalendarComponents.kt.
It assembles the view using those components and the app scaffold.
 */

package com.example.prayeveryday

import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.navigation.NavHostController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalendarScreen(navController: NavHostController) { // initializes calendar screen
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = { DisplayTopBar(scrollBehavior, "Calendar", drawerState)},
        content = { padding -> SideDrawer(drawerState, padding, Page.CALENDAR)},
        bottomBar = { DisplayNavBar(navController) }
    )
}