package com.samzuhalsetiawan.habbits.ui.composable.add_reminder_card

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.samzuhalsetiawan.habbits.models.Reminder

@Composable
fun AddReminderCard(
   reminders: List<Reminder>,
   onAddReminderButtonClick: () -> Unit,
   isEverydayReminder: Boolean = false,
   onEverydayReminderChange: (Boolean) -> Unit,
) {
   ElevatedCard {
      Column(
         modifier = Modifier.padding(16.dp)
      ) {
         Text(
            text = "Pengingat",
            style = MaterialTheme.typography.labelLarge
         )
         LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
         ) {
            items(
               items = reminders,
               key = {
                  it.time
               },
               itemContent = {
                  Row(
                     modifier = Modifier.fillMaxWidth(),
                     horizontalArrangement = Arrangement.Center,
                     verticalAlignment = Alignment.CenterVertically
                  ) {
                     OutlinedButton(onClick = { }) {
                        Text(text = it.formattedTime)
                     }
                     IconButton(onClick = { }) {
                        Icon(
                           imageVector = Icons.Default.Delete,
                           contentDescription = "Hapus reminder",
                           tint = MaterialTheme.colorScheme.error
                        )
                     }
                  }
               }
            )
            item {
               TextButton(onClick = onAddReminderButtonClick) {
                  Icon(imageVector = Icons.Default.Add, contentDescription = "Tambah pengingat")
                  Text(text = "Tambahkan Pengingat")
               }
            }
         }
         Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
         ) {
            Switch(checked = isEverydayReminder, onCheckedChange = onEverydayReminderChange)
            Text(
               modifier = Modifier.padding(start = 8.dp),
               text = "Ingatkan saya tiap hari",
               style = MaterialTheme.typography.bodyMedium
            )
         }
      }
   }
}

@Preview(showBackground = true)
@Composable
fun AddReminderCardPreview() {
   MaterialTheme {
      Surface(
         modifier = Modifier.fillMaxSize()
      ) {
         Box(
            modifier = Modifier.padding(24.dp),
            contentAlignment = Alignment.Center
         ) {
            AddReminderCard(
               reminders = emptyList(),
               onAddReminderButtonClick = { },
               onEverydayReminderChange = { }
            )
         }
      }
   }
}