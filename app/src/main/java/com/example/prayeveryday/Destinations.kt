/*
This file holds all of the data for the tabs in the bottom nav bar.
The bottom nav bar uses this data to define how each tab button looks and to tell the NavController
where to go when a tab is selected.
 */

package com.example.prayeveryday

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.DateRange
import androidx.compose.material.icons.rounded.MailOutline
import androidx.compose.ui.graphics.vector.ImageVector

interface Destinations {  // holds data for navigation between screens
    val name: String
    val route: String
    val icon: ImageVector
}

object Today:Destinations {
    override val name = "Today"
    override val route = "Today"
    override val icon = Icons.Rounded.MailOutline
}

object Calendar:Destinations {
    override val name = "Calendar"
    override val route = "Calendar"
    override val icon = Icons.Rounded.DateRange
}

object NewPrayerRequest:Destinations {
    override val name = "New"
    override val route = "NewPrayerRequest"
    override val icon = Icons.Rounded.Add
}