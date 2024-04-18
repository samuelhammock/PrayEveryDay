/*
This file holds all of the components that make up the today view.
TodayView.kt calls and assembles these components along with the app scaffold.
 */

package com.example.prayeveryday

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.time.LocalDate


@Composable
fun DisplayScrollContent(innerPadding: PaddingValues, viewModel: PrayerRequestViewModel) { // displays a list of the prayer requests for today
    viewModel.getAllFromDate(LocalDate.now())
    val requests by viewModel.requests.observeAsState(listOf())

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
    ) {
        items(requests) { item ->
            DisplayScrollItem(item = item)
        }
    }
}

@Composable
fun DisplayScrollItem(item: PrayerRequest) { // displays a single prayer request in the list
    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(1.dp)
    ) {
        Text(
            text = item.label,
            fontWeight = FontWeight.SemiBold,
            fontSize = 14.sp,
            modifier = Modifier.padding(3.dp)
        )
        Text(
            text = item.summary,
            fontWeight = FontWeight.Normal,
            fontSize = 12.sp,
            modifier = Modifier.padding(start = 10.dp, top = 3.dp, bottom = 5.dp)
        )
    }
}