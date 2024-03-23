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
import androidx.compose.runtime.remember
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
fun DisplayCalendarContent(innerPadding: PaddingValues) {
    val currentMonth = remember { YearMonth.now() }
    val startMonth = remember { currentMonth.minusMonths(100) } // Adjust as needed
    val endMonth = remember { currentMonth.plusMonths(100) } // Adjust as needed
    val firstDayOfWeek = remember { firstDayOfWeekFromLocale() } // Available from the library

    val state = rememberCalendarState(
        startMonth = startMonth,
        endMonth = endMonth,
        firstVisibleMonth = currentMonth,
        firstDayOfWeek = firstDayOfWeek
    )

    Column(modifier = Modifier.padding(innerPadding)) {
        Text(text = (DateFormat.format("MMMM", Date()) as String),
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            modifier = Modifier.padding(5.dp))
        HorizontalCalendar(  // https://github.com/kizitonwose/Calendar/tree/main?tab=readme-ov-filew
            modifier = Modifier.border(Dp.Hairline, MaterialTheme.colorScheme.tertiary),
            state = state,
            dayContent = { Day(it) }
        )
        LazyColumn(modifier = Modifier.fillMaxWidth()
            .padding(2.dp)) {
            items(scrollItems) { item ->
                DisplayScrollItem(item = item)
            }
        }
    }
}

@Composable
fun Day(day: CalendarDay) {
    Box(
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
