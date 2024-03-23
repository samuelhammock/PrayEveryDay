package com.example.prayeveryday

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.runtime.Composable

@Composable
fun SideDrawer(drawerState: DrawerState, innerPadding: PaddingValues) {
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {

                // have to figure out how to get DisplayScrollItems (or content for given page) to be here

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
        }
    ) {}
}