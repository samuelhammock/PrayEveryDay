package com.example.prayeveryday

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.DateRange
import androidx.compose.material.icons.rounded.MailOutline
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class BottomNavItem( // class for bottom nav bar items
    val name: String,
    val route: String,
    val icon: ImageVector
)

val bottomNavItems = listOf( // list of bottom nav bar items
    BottomNavItem(
        name = "New",
        route = "create",
        icon = Icons.Rounded.Add
    ),
    BottomNavItem(
        name = "Today",
        route = "today",
        icon = Icons.Rounded.MailOutline
    ),
    BottomNavItem(
        name = "Calendar",
        route = "Calendar",
        icon = Icons.Rounded.DateRange
    ),
)

@Composable
fun DisplayNavBar() {
    NavigationBar(
        containerColor = MaterialTheme.colorScheme.primaryContainer
    ) {
        bottomNavItems.forEach { item ->
            NavigationBarItem(
                selected = false,
                onClick = { /*TODO*/ },
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
fun DisplayTopBar(scrollBehavior: TopAppBarScrollBehavior?, header: String) {
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
            IconButton(onClick = { /* do something */ }) {
                Icon(
                    imageVector = Icons.Filled.Menu,
                    contentDescription = "Navigation Drawer Button"
                )
            }
        },
        scrollBehavior = scrollBehavior
    )
}