/*
This file holds all of the components that make up the today view.
TodayView.kt calls and assembles these components along with the app scaffold.
 */

package com.example.prayeveryday

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun DisplayScrollContent(innerPadding: PaddingValues) { // displays a list of the prayer requests for today
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(innerPadding)
    ) {
        items(scrollItems) { item ->
            DisplayScrollItem(item = item)
        }
    }
}

@Composable
fun DisplayScrollItem(item: ScrollItem) { // displays a single prayer request in the list
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