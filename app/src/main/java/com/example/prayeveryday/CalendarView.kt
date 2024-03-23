package com.example.prayeveryday

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.Preview

class CalendarActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SetCalendarScreen()
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Preview
    @Composable
    fun SetCalendarScreen() {
        val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
        val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

        Scaffold(
            modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
            topBar = { DisplayTopBar(scrollBehavior, "Calendar", drawerState)},
            content = { padding -> SideDrawer(drawerState, padding, Page.CALENDAR)},
            bottomBar = { DisplayNavBar() }
            )
    }
}
