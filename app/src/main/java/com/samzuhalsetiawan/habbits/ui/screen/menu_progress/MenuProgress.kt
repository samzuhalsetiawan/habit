package com.samzuhalsetiawan.habbits.ui.screen.menu_progress

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavBackStackEntry
import com.samzuhalsetiawan.habbits.App
import com.samzuhalsetiawan.habbits.ui.composable.detail_card.DetailCard
import com.samzuhalsetiawan.habbits.utils.provideViewModel
import kotlin.random.Random

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MenuProgressTopAppBar(
   scrollBehavior: TopAppBarScrollBehavior?,
   modifier: Modifier = Modifier,
) {
   MediumTopAppBar(
      modifier = modifier,
      title = {
         Column {
            Text(
               text = "Progress Saya",
            )
         }
      },
      navigationIcon = {
         IconButton(onClick = { /* do something */ }) {
            Icon(
               imageVector = Icons.Default.Menu,
               contentDescription = "Menu"
            )
         }

      },
      actions = {
         IconButton(onClick = { /* do something */ }) {
            Icon(
               imageVector = Icons.Filled.Notifications,
               contentDescription = "Localized description"
            )
         }
         IconButton(onClick = { /* do something */ }) {
            Icon(
               imageVector = Icons.AutoMirrored.Filled.List,
               contentDescription = "Localized description"
            )
         }
         IconButton(onClick = { /* do something */ }) {
            Icon(
               imageVector = Icons.Filled.MoreVert,
               contentDescription = "Localized description"
            )
         }
      },
      scrollBehavior = scrollBehavior
   )
}

@Composable
fun MenuProgress(
   navBackStackEntry: NavBackStackEntry?
) {
   val menuProgressViewModel = provideViewModel(navBackStackEntry) {
      MenuProgressViewModel(App.repositoryModule.mainRepository)
   }
   val state by menuProgressViewModel.state.collectAsStateWithLifecycle()
   MenuProgress(state = state)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MenuProgress(
   state: MenuProgressViewModel.State,
   modifier: Modifier = Modifier
) {
   var tabState by remember { mutableIntStateOf(0) }
   val titles = listOf("Semua Habit", "Selesai", "Terlewat")
   val habitsWithHistories = state.habitsWithHistories

   Surface(
      modifier = modifier.fillMaxSize(),
      color = MaterialTheme.colorScheme.background
   ) {
      Column(
         modifier = Modifier.fillMaxSize()
      ) {
         PrimaryTabRow(selectedTabIndex = tabState) {
            titles.forEachIndexed { index, title ->
               Tab(
                  selected = tabState == index,
                  onClick = { tabState = index },
                  text = {
                     Text(
                        text = buildAnnotatedString {
                           appendLine(title)
                           withStyle(
                              style = MaterialTheme.typography.bodySmall.toSpanStyle()
                           ) {
                              append(Random.nextInt(0, 20).toString())
                           }
                        },
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                     )
                  }
               )
            }
         }
         when (tabState) {
            0 -> { TabDaily(habitsWithHistories) }
            1 -> { TabMonthly() }
            2 -> {}
         }

      }
   }
}