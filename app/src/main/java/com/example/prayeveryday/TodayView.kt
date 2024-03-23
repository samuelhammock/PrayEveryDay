package com.example.prayeveryday

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.Preview

data class ScrollItem( // class for scrollable items on home screen
    val label: String,
    val summary: String,
    val details: String
)

val scrollItems = listOf( // list of scroll items for testing
    ScrollItem(
        label = "Test item 1",
        summary = "jfirst item to be tested",
        details = "the quick brown fox jumped over the lazy dog"
    ),
    ScrollItem(
        label = "test item 2",
        summary = "second item to be tested",
        details = "the quick brown fox jumped over the lazy dog"
    ),
    ScrollItem(
        label = "test item 3",
        summary = "third item to be tested",
        details = "the quick brown fox jumped over the lazy dog"
    ),
    ScrollItem(
        label = "test item 4",
        summary = "fourth item to be tested",
        details = "the quick brown fox jumped over the lazy dog"
    ),
    ScrollItem(
        label = "test item 5",
        summary = "fifth item to be tested",
        details = "the quick brown fox jumped over the lazy dog"
    ),
    ScrollItem(
        label = "test item 6",
        summary = "sixth item to be tested",
        details = "the quick brown fox jumped over the lazy dog"
    ),
    ScrollItem(
        label = "test item 7",
        summary = "seventh item to be tested",
        details = "the quick brown fox jumped over the lazy dog"
    ),
    ScrollItem(
        label = "test item 8",
        summary = "eighth item to be tested",
        details = "the quick brown fox jumped over the lazy dog"
    ),
    ScrollItem(
        label = "test item 9",
        summary = "ninth item to be tested",
        details = "the quick brown fox jumped over the lazy dog"
    ),ScrollItem(
        label = "test item 10",
        summary = "tenth item to be tested",
        details = "the quick brown fox jumped over the lazy dog"
    ),
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SetMainScreen()
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Preview
    @Composable
    fun SetMainScreen() {
        val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
        val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

        Scaffold(
            modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
            topBar = { DisplayTopBar(scrollBehavior, "Today's Prayer Requests", drawerState) },
            content = { padding ->  SideDrawer(drawerState, padding, Page.TODAY)},
            bottomBar = { DisplayNavBar() }
        )
    }
}

