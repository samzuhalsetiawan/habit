package com.wahyusembiring.habit.presentation.composable.tab

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.text.style.TextOverflow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PrimaryTab(
   titles: List<String>,
   tabState: MutableIntState = rememberTabState()
) {
   PrimaryTabRow(selectedTabIndex = tabState.intValue) {
      titles.forEachIndexed { index, title ->
         Tab(
            selected = tabState.intValue == index,
            onClick = { tabState.intValue = index },
            text = { Text(text = title, maxLines = 2, overflow = TextOverflow.Ellipsis) }
         )
      }
   }
}

@Composable
fun rememberTabState() = remember { mutableIntStateOf(0) }