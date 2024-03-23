package com.example.prayeveryday

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.runtime.Composable

enum class Page {
    TODAY, CALENDAR, NEW, NOTIFICATIONS
}

@Composable
fun SideDrawer(drawerState: DrawerState, innerPadding: PaddingValues, page: Page) {
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                /*
                Column(modifier = Modifier.fillMaxHeight()
                    .width(50.dp)) {
                    Row(modifier = Modifier.fillMaxWidth()
                        .height(50.dp)
                        .background(MaterialTheme.colorScheme.secondary) ){
                    }
                }

                 */
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