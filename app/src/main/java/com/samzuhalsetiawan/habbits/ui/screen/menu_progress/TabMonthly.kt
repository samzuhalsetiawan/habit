package com.samzuhalsetiawan.habbits.ui.screen.menu_progress

import com.samzuhalsetiawan.habbits.R
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Badge
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import com.kizitonwose.calendar.compose.HorizontalCalendar
import com.kizitonwose.calendar.compose.rememberCalendarState
import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.core.DayPosition
import com.kizitonwose.calendar.core.daysOfWeek
import com.kizitonwose.calendar.core.firstDayOfWeekFromLocale
import java.time.DayOfWeek
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.Locale

@Composable
fun TabMonthly() {
   val currentMonth = remember { YearMonth.now() }
   val startMonth = remember { currentMonth.minusMonths(100) } // Adjust as needed
   val endMonth = remember { currentMonth.plusMonths(100) } // Adjust as needed
   val firstDayOfWeek = remember { firstDayOfWeekFromLocale() } // Available from the library
//   val daysOfWeek = remember { daysOfWeek() } // Available in the library

   val state = rememberCalendarState(
      startMonth = startMonth,
      endMonth = endMonth,
      firstVisibleMonth = currentMonth,
      firstDayOfWeek = firstDayOfWeek
   )

   Column {
      HorizontalCalendar(
         state = state,
         dayContent = { Day(it) },
         monthHeader = { month ->
            val daysOfWeek = month.weekDays.first().map { it.date.dayOfWeek }
            MonthHeader(daysOfWeek = daysOfWeek)
         }
      )
   }
}

@Composable
fun MonthHeader(daysOfWeek: List<DayOfWeek>) {
   DaysOfWeekTitle(daysOfWeek = daysOfWeek)
}

@Composable
fun Day(day: CalendarDay) {
   Box(
      modifier = Modifier
         .aspectRatio(1f), // This is important for square sizing!
      contentAlignment = Alignment.TopStart
   ) {
      Column {
         Badge(
            containerColor = colorResource(id = R.color.customYellow),
            content = { Text(text = "1 pending") }
         )
         Badge(
            containerColor = colorResource(id = R.color.customRed)
         )
      }
      Text(
         text = day.date.dayOfMonth.toString(),
         color = if (day.position == DayPosition.MonthDate) MaterialTheme.colorScheme.onSurface else Color.Gray
      )
   }
}

@Composable
fun DaysOfWeekTitle(daysOfWeek: List<DayOfWeek>) {
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