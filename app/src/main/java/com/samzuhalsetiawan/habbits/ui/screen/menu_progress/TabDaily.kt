package com.samzuhalsetiawan.habbits.ui.screen.menu_progress

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.samzuhalsetiawan.habbits.models.Habit
import com.samzuhalsetiawan.habbits.models.HabitHistory
import com.samzuhalsetiawan.habbits.ui.composable.detail_card.DetailCard

@Composable
fun TabDaily(
   habitsWithHistories: List<Pair<Habit, List<HabitHistory>>>
) {
   LazyColumn(
      modifier = Modifier
         .fillMaxSize()
         .padding(horizontal = 16.dp)
   ) {
      items(
         count = habitsWithHistories.size,
         key = { habitsWithHistories[it].first.id }
      ) {
         val habit = habitsWithHistories[it].first
         val histories = habitsWithHistories[it].second
         DetailCard(
            habit = habit,
            histories = histories,
            modifier = Modifier
               .fillMaxWidth()
               .padding(top = 16.dp)
         )
         Spacer(modifier = Modifier.height(16.dp))
      }
   }
}