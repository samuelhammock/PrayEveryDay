/*
This file holds all of the components that make up the calendar view.
CalendarView.kt calls and assembles these components along with the app scaffold.
 */

package com.example.prayeveryday

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kizitonwose.calendar.compose.HorizontalCalendar
import com.kizitonwose.calendar.compose.rememberCalendarState
import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.core.DayPosition
import com.kizitonwose.calendar.core.daysOfWeek
import java.time.DayOfWeek
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.Locale


@Composable
fun DisplayCalendarContent(innerPadding: PaddingValues, viewModel: PrayerRequestViewModel) { // displays calendar view within scaffold, with list of requests below
    val currentMonth = rememberSaveable { YearMonth.now() } // stores the month displayed at top of calendar
    val startMonth = rememberSaveable { currentMonth.minusMonths(100) } // maximum number of months before current month
    val endMonth = rememberSaveable { currentMonth.plusMonths(100) } // maximum number of months after current month
    val daysOfWeek = rememberSaveable { daysOfWeek() }

    val state = rememberCalendarState(
        startMonth = startMonth,
        endMonth = endMonth,
        firstVisibleMonth = currentMonth,
        firstDayOfWeek = daysOfWeek.first()
    )

    Column(modifier = Modifier.padding(innerPadding)) {
        HorizontalCalendar(  // https://github.com/kizitonwose/Calendar/tree/main?tab=readme-ov-filew
            modifier = Modifier.border(Dp.Hairline, MaterialTheme.colorScheme.tertiary),
            state = state,
            dayContent = { Day(it) },  // determines appearance of each calendar block
            monthHeader = {
                Text( text = state.lastVisibleMonth.yearMonth.month.toString() // DO NOT TOUCH: Idk how this gets the month but it does.
                    .lowercase()
                    .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() }, // it's a mess but it works
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp,
                    modifier = Modifier.padding(5.dp))
                DaysOfWeekTitle(daysOfWeek = daysOfWeek) // shows days of week above calendar
            }
        )
        /*
        LazyColumn(modifier = Modifier // displays selected day's prayer requests below calendar
            .fillMaxWidth()
            .padding(2.dp)) {
            items(scrollItems) { item ->
                DisplayScrollItem(item = item)
            }
        }

         */
    }
}

@Composable
fun Day(day: CalendarDay) { // https://github.com/kizitonwose/Calendar/tree/main?tab=readme-ov-filew
    Box( // each box is a day on the calendar
        modifier = if (day.position == DayPosition.MonthDate) // displays different color for day boxes outside current month
            Modifier
                .border(Dp.Hairline, MaterialTheme.colorScheme.tertiary)
                .aspectRatio(1f) // This is important for square sizing!
                .background(Color.White)
            else Modifier
            .border(Dp.Hairline, MaterialTheme.colorScheme.tertiary)
            .aspectRatio(1f) // This is important for square sizing!
            .background(Color.LightGray),

        contentAlignment = Alignment.TopEnd
    ) {
        Text(text = day.date.dayOfMonth.toString(),
            color = if (day.position == DayPosition.MonthDate) // displays different color for day numbers outside of month
                MaterialTheme.colorScheme.tertiary else Color.DarkGray,
            fontSize = 12.sp,
            modifier = Modifier.padding(2.dp))
    }
}

@Composable
fun DaysOfWeekTitle(daysOfWeek: List<DayOfWeek>) { // shows days of week above calendar
    Row(modifier = Modifier.fillMaxWidth()) {
        for (dayOfWeek in daysOfWeek) {
            Text(
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center,
                text = dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.getDefault()),
            )
        }
    }
}
