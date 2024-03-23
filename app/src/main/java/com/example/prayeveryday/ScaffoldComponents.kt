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
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.DateRange
import androidx.compose.material.icons.rounded.MailOutline
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import kotlinx.coroutines.launch

data class BottomNavItem( // class for bottom nav bar items
    val name: String,
    val route: String,
    val icon: ImageVector
)

enum class Page {
    TODAY, CALENDAR, NEW, NOTIFICATIONS
}

val bottomNavItems = listOf( // list of bottom nav bar items
    BottomNavItem(
        name = "New",
        route = NewPrayerRequest.route,
        icon = Icons.Rounded.Add
    ),
    BottomNavItem(
        name = "Today",
        route = Today.route,
        icon = Icons.Rounded.MailOutline
    ),
    BottomNavItem(
        name = "Calendar",
        route = Calendar.route,
        icon = Icons.Rounded.DateRange
    ),
)

@Composable
fun DisplayNavBar(navController: NavHostController) {
    NavigationBar(
        containerColor = MaterialTheme.colorScheme.primaryContainer
    ) {
        bottomNavItems.forEach { item ->
            NavigationBarItem(
                selected = false,
                onClick = { navController.navigate(item.route) },
                label = {
                    Text(
                        text = item.name,
                        fontWeight = FontWeight.SemiBold,
                    )
                },
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = "${item.name} Icon",
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