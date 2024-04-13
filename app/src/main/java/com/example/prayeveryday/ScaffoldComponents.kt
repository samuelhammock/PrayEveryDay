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
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import kotlinx.coroutines.launch

enum class Page {
    TODAY, CALENDAR, NEW, NOTIFICATIONS
}

@Composable
fun DisplayNavBar(navController: NavHostController) {
    val navList = listOf<Destinations>(
        NewPrayerRequest,
        Today,
        Calendar
    )
    // This article's writing may be useless but the code is good https://medium.com/geekculture/bottom-navigation-in-jetpack-compose-android-9cd232a8b16
    // helped me figure out how to highlight the selected item on the nav bar
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    NavigationBar(
        containerColor = MaterialTheme.colorScheme.primaryContainer
    ) {
        navList.forEach {  destination ->
            NavigationBarItem(
                selected = currentRoute == destination.route,
                onClick = {
                    navController.navigate(destination.route){
                        popUpTo(Today.route)
                        launchSingleTop = true
                    }
                },
                label = {
                    Text(
                        text = destination.name,
                        fontWeight = FontWeight.SemiBold,
                    )
                },
                icon = {
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
fun DisplayTopBar(scrollBehavior: TopAppBarScrollBehavior?, header: String, drawerState: DrawerState) {
    val scope = rememberCoroutineScope()

    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.primary
        ),
        title = {
            Text(
                text = header,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontSize = 18.sp,
                modifier = Modifier.padding(start = 5.dp)
            )
        },
        navigationIcon = {
            IconButton(onClick =
            {
                scope.launch {
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
fun SideDrawer(drawerState: DrawerState, innerPadding: PaddingValues, page: Page) {
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
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
        content = {
            when(page) {
                Page.TODAY -> DisplayScrollContent(innerPadding = innerPadding)
                Page.CALENDAR -> DisplayCalendarContent(innerPadding = innerPadding)
                Page.NEW -> DisplayNewPrayerRequestContent(innerPadding = innerPadding)
                Page.NOTIFICATIONS -> TODO()
            }
        }
    )
}