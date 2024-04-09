package com.example.prayeveryday

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.DateRange
import androidx.compose.material.icons.rounded.MailOutline
import androidx.compose.ui.graphics.vector.ImageVector

interface Destinations {
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