/*
This file holds all of the components that make up the today view.
TodayView.kt calls and assembles these components along with the app scaffold.
 */

package com.example.prayeveryday

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import java.time.LocalDate


@Composable
fun DisplayScrollContent(innerPadding: PaddingValues, viewModel: PrayerRequestViewModel, date: LocalDate) { // displays a list of the prayer requests for a given day, also used for the calendar screen
    viewModel.getAllFromDate(date)
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
    val showDialog = rememberSaveable { mutableStateOf(false) }

    if(showDialog.value) {
        ShowFullDetailsDialog(item, showDialog)
    }

    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(1.dp)
            .clickable(
                onClick = { showDialog.value = true }
            )
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

@Composable
fun ShowFullDetailsDialog(request: PrayerRequest, showDialog: MutableState<Boolean>) {
    Dialog(
        onDismissRequest = { showDialog.value = false },
        content = {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(400.dp)
                    .padding(10.dp),
                shape = RoundedCornerShape(16.dp),
            ) {
                Column(modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.SpaceBetween) {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        Row(horizontalArrangement = Arrangement.End,
                            modifier = Modifier.fillMaxWidth()) {
                            Button(
                                colors = ButtonColors(
                                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                                    contentColor = Color.Red,
                                    disabledContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                                    disabledContentColor = Color.Red
                                ),
                                content = { Text(text = "Delete") },
                                onClick = { showDialog.value = false }
                            )
                        }
                        LazyColumn() {
                            item() {
                                Text(
                                    text = request.label,
                                    modifier = Modifier.padding(3.dp),
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                            item() {
                                Text(
                                    text = request.summary,
                                    modifier = Modifier.padding(3.dp),
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Normal
                                )
                            }
                            item() {
                                Text(
                                    text = request.details,
                                    modifier = Modifier.padding(3.dp),
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Normal
                                )
                            }
                        }
                    }
                }
            }
        }
    )
}