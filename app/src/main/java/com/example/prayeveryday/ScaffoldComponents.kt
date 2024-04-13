/*
This file defines all of the components of the app scaffold.  The scaffold is used in every
page in the app.
These components are called as needed from each view to fill the scaffold.
 */

package com.example.prayeveryday

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import kotlinx.coroutines.launch

enum class Page { // enum to store what page the app is currently displaying
    TODAY, CALENDAR, NEW, NOTIFICATIONS
}

@Composable
fun DisplayNavBar(navController: NavHostController) { // The bottom nav bar that allows navigaton between pages
    val navList = listOf<Destinations>(  // the destinations from Destinations.kt
        NewPrayerRequest,
        Today,
        Calendar
    )
    // This article's writing may be useless but the code is good https://medium.com/geekculture/bottom-navigation-in-jetpack-compose-android-9cd232a8b16
    // helped me figure out how to highlight the selected item on the nav bar
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route // stores the current page

    NavigationBar(
        containerColor = MaterialTheme.colorScheme.primaryContainer
    ) {
        navList.forEach {  destination -> // displays each option in nav bar
            NavigationBarItem(
                selected = currentRoute == destination.route,
                onClick = { // navigates to selected page and resets backstack
                    navController.navigate(destination.route){
                        popUpTo(Today.route)
                        launchSingleTop = true
                    }
                },
                label = { // label for each nav option
                    Text(
                        text = destination.name,
                        fontWeight = FontWeight.SemiBold,
                    )
                },
                icon = { // icon for each nav option
                    Icon(
                        imageVector = destination.icon,
                        contentDescription = "${destination.name} Icon",
                    )
                }
            )
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
// the top bar with name of current page and button to open side drawer
fun DisplayTopBar(scrollBehavior: TopAppBarScrollBehavior?, header: String, drawerState: DrawerState) {
    val scope = rememberCoroutineScope() // coroutine scope needed for concurrent animation of drawer opening

    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.primary
        ),
        title = {
            Text( // displays name of current page
                text = header,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontSize = 18.sp,
                modifier = Modifier.padding(start = 5.dp)
            )
        },
        navigationIcon = {
            IconButton(onClick = // button to open side drawer
            {
                scope.launch { // launches coroutine so drawer animation can be completed concurrently
                    drawerState.apply {
                        if (isClosed) open() else close()
                    }
                }
            }) {
                Icon(
                    imageVector = Icons.Filled.Menu,
                    contentDescription = "Navigation Drawer Button"
                )
            }
        },
        scrollBehavior = scrollBehavior
    )
}

@Composable
fun SideDrawer(drawerState: DrawerState, innerPadding: PaddingValues, page: Page) { // side drawer containing buttons
    ModalNavigationDrawer(
        drawerState = drawerState, // stores whether drawer is open or closed
        drawerContent = { // specifies what is displayed in drawer
            ModalDrawerSheet {
                Column(modifier = Modifier.fillMaxHeight()
                    .width(50.dp)) {
                    Row(modifier = Modifier.fillMaxWidth()
                        .height(50.dp)
                        .background(MaterialTheme.colorScheme.secondary) ){
                    }
                }


            }
        },
        content = { // defines what is behind drawer, which is the main content of each page
            // seems like a weird place to put it structurally but I don't make the rules
            when(page) {
                Page.TODAY -> DisplayScrollContent(innerPadding = innerPadding)
                Page.CALENDAR -> DisplayCalendarContent(innerPadding = innerPadding)
                Page.NEW -> DisplayNewPrayerRequestContent(innerPadding = innerPadding)
                Page.NOTIFICATIONS -> TODO()
            }
        }
    )
}