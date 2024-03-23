package com.example.prayeveryday

interface Destinations {
    val route: String
}

object Today:Destinations {
    override val route = "Today"
}

object Calendar:Destinations {
    override val route = "Calendar"
}

object NewPrayerRequest:Destinations {
    override val route = "NewPrayerRequest"
}