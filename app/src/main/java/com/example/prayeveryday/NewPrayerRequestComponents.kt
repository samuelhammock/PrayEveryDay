package com.example.prayeveryday

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import java.text.SimpleDateFormat
import java.util.Date


@Composable
fun DisplayNewPrayerRequestContent(innerPadding: PaddingValues) {
    var requestLabel by remember { mutableStateOf("Label") }
    var requestSummary by remember { mutableStateOf("Summary") }
    var requestDetails by remember { mutableStateOf("Details") }
    var requestDate by remember { mutableStateOf("mm/dd/yyyy") }
    val scrollState = rememberScrollState()
    var menuExpanded by remember { mutableStateOf(false) }
    var menuTextFieldSize by remember { mutableStateOf(Size.Zero) }
    val menuOptions = listOf("Do Not Repeat", "Repeat Daily", "Repeat Weekly", "Repeat Monthly","Repeat Yearly")
    var menuSelectedText by remember { mutableStateOf("Do Not Repeat") }
    val icon = if (menuExpanded)
        Icons.Filled.KeyboardArrowUp
    else
        Icons.Filled.KeyboardArrowDown


    Column(modifier = Modifier
        .padding(innerPadding)
        .fillMaxWidth()
        .verticalScroll(scrollState)) {
        TextField(
            modifier = Modifier
                .padding(2.dp)
                .fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            value = requestLabel,
            onValueChange = { requestLabel = it },
            label = { Text("Label") }
        )

        Card(shape = RoundedCornerShape(8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(1.dp)) {
            Row(modifier = Modifier.padding(start = 7.dp),
                horizontalArrangement = Arrangement.SpaceBetween) {

                Column( modifier = Modifier.padding(5.dp)) {
                    TextField(modifier = Modifier.size(115.dp, 50.dp),
                        value = requestDate,
                        shape = RoundedCornerShape(8.dp),
                        label = { Text("Date to pray") },
                        onValueChange = { requestDate = it })
                }
                OutlinedTextField(
                    value = menuSelectedText,
                    onValueChange = { menuSelectedText = it },
                    modifier = Modifier.padding(5.dp)
                        .onGloballyPositioned { coordinates ->
                            // This value is used to assign to
                            // the DropDown the same width
                            menuTextFieldSize = coordinates.size.toSize()
                        },
                    trailingIcon = {
                        Icon(icon,"contentDescription",
                            Modifier.clickable { menuExpanded = !menuExpanded })
                    }
                )
                DropdownMenu(
                    expanded = menuExpanded,
                    onDismissRequest = { menuExpanded = false },
                    modifier = Modifier
                        .width(with(LocalDensity.current){menuTextFieldSize.width.toDp()})
                ) {
                    menuOptions.forEach { label ->
                        DropdownMenuItem(text = { Text(text = label) },
                            onClick = {
                                menuSelectedText = label
                                menuExpanded = false })
                    }
                }
            }
        }
        TextField(
            modifier = Modifier
                .padding(2.dp)
                .fillMaxWidth()
                .height(80.dp),
            shape = RoundedCornerShape(8.dp),
            value = requestSummary,
            onValueChange = { requestSummary = it },
            label = { Text("Summary") }
        )
        TextField(
            modifier = Modifier
                .padding(2.dp)
                .fillMaxWidth()
                .height(300.dp),
            shape = RoundedCornerShape(8.dp),
            value = requestDetails,
            onValueChange = { requestDetails = it },
            label = { Text("Details") },
            singleLine = false
        )
        Row(modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End) {

            Button(content = { Text(text = "Save") },
                onClick = { /* TODO */ },
                shape = RoundedCornerShape(8.dp)
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerView() {
    val datePickerState = rememberDatePickerState(selectableDates = object : SelectableDates {
        override fun isSelectableDate(utcTimeMillis: Long): Boolean {
            return utcTimeMillis <= System.currentTimeMillis()
        }
    })
    val selectedDate = datePickerState.selectedDateMillis?.let {
        convertMillisToDate(it)
    }
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        DatePicker(
            state = datePickerState
        )
        Spacer(
            modifier = Modifier.height(
                32.dp
            )
        )
        Text(
            text = selectedDate.toString(),
            color = Color.Red
        )
    }
}

@SuppressLint("SimpleDateFormat")
private fun convertMillisToDate(millis: Long): String {
    val formatter = SimpleDateFormat("dd/MM/yyyy")
    return formatter.format(Date(millis))
}