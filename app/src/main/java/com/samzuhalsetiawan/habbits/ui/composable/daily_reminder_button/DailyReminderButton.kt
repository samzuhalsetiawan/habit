package com.samzuhalsetiawan.habbits.ui.composable.daily_reminder_button

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kizitonwose.calendar.core.daysOfWeek
import java.time.DayOfWeek
import java.time.format.TextStyle
import java.util.Locale


@Composable
fun DailyReminderButton(
   modifier: Modifier = Modifier,
   selectedDays: SnapshotStateList<DayOfWeek> = mutableStateListOf(),
) {
   DailyReminderButtonContainer(
      modifier = modifier,
      selectedDays = selectedDays,
   )
}

@Composable
private fun DailyReminderButtonContainer(
   modifier: Modifier = Modifier,
   selectedDays: SnapshotStateList<DayOfWeek>,
) {
   val daysOfWeek = daysOfWeek()

   Row(
      modifier = modifier
   ) {
      repeat(7) {
         DailyReminderButton(
            day = daysOfWeek[it],
            selected = { day ->
               selectedDays.contains(day)
            },
            onClick = { day -> if (!selectedDays.remove(day)) selectedDays.add(day) },
         )
      }
   }
}

@Composable
private fun RowScope.DailyReminderButton(
   day: DayOfWeek,
   selected: (DayOfWeek) -> Boolean,
   onClick: (DayOfWeek) -> Unit,
) {
   OutlinedButton(
      onClick = { onClick(day) },
      colors = ButtonDefaults.outlinedButtonColors().let {
         if (!selected(day)) return@let it
         it.copy(containerColor = MaterialTheme.colorScheme.primaryContainer)
      },
      contentPadding = PaddingValues(0.dp),
      modifier = Modifier
         .aspectRatio(1f)
         .weight(1f)
         .clip(CircleShape), // Clip toa circle
   ) {
      Text(
         textAlign = TextAlign.Center,
         text = day.getDisplayName(TextStyle.SHORT, Locale.getDefault()).take(1)
      )
   }
}

@Preview(showBackground = true)
@Composable
private fun DailyReminderButtonPreview() {
   MaterialTheme {
      Surface(
         modifier = Modifier.fillMaxSize()
      ) {
         Box(
            contentAlignment = Alignment.Center
         ) {
            DailyReminderButton()
         }
      }
   }
}