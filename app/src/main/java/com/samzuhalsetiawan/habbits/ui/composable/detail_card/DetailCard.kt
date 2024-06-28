package com.samzuhalsetiawan.habbits.ui.composable.detail_card

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.samzuhalsetiawan.habbits.models.Habit
import com.samzuhalsetiawan.habbits.models.HabitHistory

@Composable
fun DetailCard(
   habit: Habit,
   histories: List<HabitHistory>,
   modifier: Modifier = Modifier
) {
   Card(
      colors = CardDefaults.cardColors(
         containerColor = MaterialTheme.colorScheme.surfaceVariant,
      ),
      modifier = modifier
   ) {
      Text(
         text = habit.name,
         modifier = Modifier
            .padding(16.dp),
         textAlign = TextAlign.Center,
      )
      ListItem(
         modifier = Modifier
            .padding(horizontal = 16.dp)
            .background(ListItemDefaults.containerColor, RoundedCornerShape(14)),
         colors = ListItemDefaults.colors(containerColor = Color.Transparent),
         headlineContent = {
            Text(
               "Pencapaian Beruntun",
               style = MaterialTheme.typography.bodyMedium
            )
         },
         trailingContent = {
            Text(
               text = "${habit.streak} Hari",
               style = MaterialTheme.typography.labelSmall
            )
         }
      )
      Spacer(modifier = Modifier.height(8.dp))
      ListItem(
         modifier = Modifier
            .padding(horizontal = 16.dp)
            .background(ListItemDefaults.containerColor, RoundedCornerShape(14)),
         colors = ListItemDefaults.colors(containerColor = Color.Transparent),
         headlineContent = {
            Text(
               "Diselesaikan",
               style = MaterialTheme.typography.bodyMedium
            )
         },
         trailingContent = {
            Text(
               text = "${
                  histories.filter { it.status == HabitHistory.Status.COMPLETE }.size
               } Hari",
               style = MaterialTheme.typography.labelSmall
            )
         }
      )
      Spacer(modifier = Modifier.height(8.dp))
      ListItem(
         modifier = Modifier
            .padding(horizontal = 16.dp)
            .background(ListItemDefaults.containerColor, RoundedCornerShape(14)),
         colors = ListItemDefaults.colors(containerColor = Color.Transparent),
         headlineContent = { Text("Terlewat", style = MaterialTheme.typography.bodyMedium) },
         trailingContent = {
            Text(
               text = "${
                  histories.filter { it.status == HabitHistory.Status.SKIPPED }.size
               } Hari",
               style = MaterialTheme.typography.labelSmall
            )
         }
      )
      Spacer(modifier = Modifier.height(8.dp))
      ListItem(
         modifier = Modifier
            .padding(horizontal = 16.dp)
            .background(ListItemDefaults.containerColor, RoundedCornerShape(14)),
         colors = ListItemDefaults.colors(containerColor = Color.Transparent),
         headlineContent = { Text("Gagal", style = MaterialTheme.typography.bodyMedium) },
         trailingContent = {
            Text(
               text = "${
                  histories.filter { it.status == HabitHistory.Status.FAILED }.size
               } Hari",
               style = MaterialTheme.typography.labelSmall
            )
         }
      )
      Spacer(modifier = Modifier.height(16.dp))
   }
}

@Preview(showBackground = true)
@Composable
fun DetailCardPreview() {
   Surface(
      modifier = Modifier.fillMaxSize()
   ) {
      Box(
         modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
         contentAlignment = Alignment.Center
      ) {
         DetailCard(
            habit = Habit(
               name = "Membaca",
               streak = 12
            ),
            histories = emptyList()
         )
      }
   }
}