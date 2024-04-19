package com.example.prayeveryday

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import java.time.LocalTime

@Composable
fun DisplayNotificationsOptions(innerPadding: PaddingValues) {
    val context = LocalContext.current
    var notificationTime by rememberSaveable { mutableStateOf("") }
    val scheduler = AlarmScheduler(context)
    val time = LocalTime.of(22, 33)

    Column(modifier = Modifier.padding(innerPadding)) {
        OutlinedTextField(
            modifier = Modifier
                .padding(2.dp)
                .fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            value = notificationTime,
            onValueChange = { notificationTime = it },
            label = { Text("Time of Daily Notification:") }
        )
        Button(
            content = { Text("Save Daily Notification") },
            onClick = {
                scheduler.schedule(time)
                /*
                try {
                    val timeFormatter = DateTimeFormatter.ofPattern("HH:mm a")
                    val time = LocalTime.parse(notificationTime, timeFormatter)
                    scheduler.schedule(time)
                } catch(_: DateTimeParseException) {
                    Toast.makeText(context, "Please enter a date in hh:mm AM/PM format", Toast.LENGTH_SHORT).show()

                }

                 */
            }
        )
    }
}