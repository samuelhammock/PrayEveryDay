/*
This file holds all of the components that make up the new prayer request view.
NewPrayerRequestView.kt calls and assembles these components along with the app scaffold.
 */

package com.example.prayeveryday

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException


@Composable
fun DisplayNewPrayerRequestContent(innerPadding: PaddingValues, viewModel: PrayerRequestViewModel) { // displays form for creating new prayer request
    // variables to store state of form fields
    var requestLabel by rememberSaveable { mutableStateOf("") }
    var requestSummary by rememberSaveable{ mutableStateOf("") }
    var requestDetails by rememberSaveable{ mutableStateOf("") }
    var requestDate by rememberSaveable{ mutableStateOf("") }
    var repeatEndDate by rememberSaveable{ mutableStateOf("") }

    val scrollState = rememberScrollState()
    var menuExpanded by rememberSaveable{ mutableStateOf(false) } // controls whether dropdown is open
    var menuTextFieldSize by remember{ mutableStateOf(Size.Zero) }
    val menuOptions = listOf("Do Not Repeat", "Repeat Daily", "Repeat Weekly", "Repeat Monthly","Repeat Yearly")
    var menuSelectedText by rememberSaveable{ mutableStateOf("Do Not Repeat") }
    var visible by rememberSaveable{ mutableStateOf(false) } // controls whether "repeat until" field is visible
    val context = LocalContext.current // stores the local context
    val scope = rememberCoroutineScope() // used to access database if needed
    val icon = if (menuExpanded)
        Icons.Filled.KeyboardArrowUp
    else
        Icons.Filled.KeyboardArrowDown


    Column(modifier = Modifier
        .padding(innerPadding)
        .fillMaxWidth()
        .verticalScroll(scrollState)) {
        TextField( // field for request label
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
                Column(modifier =  Modifier.padding(5.dp)) {
                    Text(text = "Prayer Request Date",
                        modifier = Modifier.padding(bottom = 5.dp))
                    TextField(modifier = Modifier.size(150.dp, 50.dp), // field for prayer request date
                        value = requestDate,
                        shape = RoundedCornerShape(8.dp),
                        label = { Text("mm/dd/yyyy") },
                        onValueChange = { requestDate = it })
                }
                AnimatedVisibility(visible = visible) { // controls visibility of optional field
                    Column(modifier = Modifier.padding(5.dp)) {
                        Text(text = "Repeat Until",
                            modifier = Modifier.padding(bottom = 5.dp))
                        TextField(modifier = Modifier.size(150.dp, 50.dp), // field for end of request repetition
                            value = repeatEndDate,
                            shape = RoundedCornerShape(8.dp),
                            label = { Text("mm/dd/yy") },
                            onValueChange = { repeatEndDate = it })
                    }
                }
            }
            OutlinedTextField( // dropdown menu for repetition
                value = menuSelectedText,
                onValueChange = { menuSelectedText = it},
                readOnly = true,
                modifier = Modifier
                    .padding(5.dp)
                    .onGloballyPositioned { coordinates -> // This value is used to assign the same width to the dropdown
                        menuTextFieldSize = coordinates.size.toSize()
                    },
                trailingIcon = { // icon showing whether dropdown is open or closed
                    Icon(icon,"contentDescription",
                        Modifier.clickable { menuExpanded = !menuExpanded })
                }
            )
            DropdownMenu( // the dropdown menu itself
                expanded = menuExpanded,
                onDismissRequest = { menuExpanded = false },
                modifier = Modifier
                    .width(with(LocalDensity.current){menuTextFieldSize.width.toDp()})
            ) {
                menuOptions.forEach { label ->
                    DropdownMenuItem(text = { Text(text = label) },
                        onClick = { // set field to chosen option and close dropdown
                            menuSelectedText = label
                            menuExpanded = false
                            visible = (menuSelectedText != "Do Not Repeat")})
                }
            }
        }
        TextField( // field for summary of request
            modifier = Modifier
                .padding(2.dp)
                .fillMaxWidth()
                .height(80.dp),
            shape = RoundedCornerShape(8.dp),
            value = requestSummary,
            onValueChange = { requestSummary = it },
            label = { Text("Summary") }
        )
        TextField( // field for full description of request
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

            Button(content = { Text(text = "Save") }, // button to save request data
                onClick = {
                    if((requestLabel == "") || (requestDate == "")) {
                        Toast.makeText(context, "Please enter a date and summary", Toast.LENGTH_SHORT).show()
                    } else if((menuSelectedText != "Do Not Repeat") && (repeatEndDate == "")) {
                        Toast.makeText(context, "Please enter an end date", Toast.LENGTH_SHORT).show()
                    } else {
                        scope.launch {
                            try {
                                val dateFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy")
                                val date = LocalDate.parse(requestDate, dateFormatter) //need date to be in LocalDate format before converting to String
                                val request = PrayerRequest(
                                    uid = 0, // 0 tells database to auto-generate uid
                                    label = requestLabel,
                                    date = date.toString(),
                                    summary = requestSummary,
                                    details = requestDetails,
                                    repeating = false
                                )
                                viewModel.insertPrayerRequest(request)
                                Toast.makeText(context, "Prayer Request Saved", Toast.LENGTH_SHORT).show()
                            } catch(_: DateTimeParseException) {
                                Toast.makeText(context, "Please enter a date in mm/dd/yyyy format", Toast.LENGTH_SHORT).show()
                            }

                        }
                    }
                },
                shape = RoundedCornerShape(8.dp)
            )
        }
    }
}

/*
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerView() { // date picker to be added to date fields
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
private fun convertMillisToDate(millis: Long): String { // converts utc to date
    val formatter = SimpleDateFormat("dd/MM/yyyy")
    return formatter.format(Date(millis))
}
 */