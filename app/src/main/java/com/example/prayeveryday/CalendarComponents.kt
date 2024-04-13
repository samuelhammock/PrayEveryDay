/*
This file holds all of the components that make up the calendar view.
CalendarView.kt calls and assembles these components along with the app scaffold.
 */

package com.example.prayeveryday

import android.text.format.DateFormat
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kizitonwose.calendar.compose.HorizontalCalendar
import com.kizitonwose.calendar.compose.rememberCalendarState
import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.core.firstDayOfWeekFromLocale
import java.time.YearMonth
import java.util.Date


@Composable
fun DisplayCalendarContent(innerPadding: PaddingValues) { // displays calendar view within scaffold, with list of requests below
    val currentMonth = rememberSaveable { YearMonth.now() } // stores the month displayed at top of calendar
    val startMonth = rememberSaveable { currentMonth.minusMonths(100) } // maximum number of months before current month
    val endMonth = rememberSaveable { currentMonth.plusMonths(100) } // maximum number of months after current month
    val firstDayOfWeek = rememberSaveable { firstDayOfWeekFromLocale() }

    val state = rememberCalendarState(
        startMonth = startMonth,
        endMonth = endMonth,
        firstVisibleMonth = currentMonth,
        firstDayOfWeek = firstDayOfWeek
    )

    Column(modifier = Modifier.padding(innerPadding)) {
        Text(text = (DateFormat.format("MMMM", Date()) as String), // displays name of current month
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            modifier = Modifier.padding(5.dp))
        HorizontalCalendar(  // https://github.com/kizitonwose/Calendar/tree/main?tab=readme-ov-filew
            modifier = Modifier.border(Dp.Hairline, MaterialTheme.colorScheme.tertiary),
            state = state,
            dayContent = { Day(it) }  // determines appearance of each calendar block
        )
        LazyColumn(modifier = Modifier // displays selected day's prayer requests below calendar
            .fillMaxWidth()
            .padding(2.dp)) {
            items(scrollItems) { item ->
                DisplayScrollItem(item = item)
            }
        }
    }
}

@Composable
fun Day(day: CalendarDay) { // https://github.com/kizitonwose/Calendar/tree/main?tab=readme-ov-filew
    Box( // each box is a day on the calendar
        modifier = Modifier
            .border(Dp.Hairline, MaterialTheme.colorScheme.tertiary)
            .aspectRatio(1f), // This is important for square sizing!
        contentAlignment = Alignment.TopEnd
    ) {
        Text(text = day.date.dayOfMonth.toString(),
            fontSize = 12.sp,
            modifier = Modifier.padding(2.dp))
    }
}
