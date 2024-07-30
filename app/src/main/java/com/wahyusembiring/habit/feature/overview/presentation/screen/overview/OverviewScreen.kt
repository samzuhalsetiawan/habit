package com.wahyusembiring.habit.feature.overview.presentation.screen.overview

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.wahyusembiring.habit.App
import com.wahyusembiring.habit.R
import com.wahyusembiring.habit.core.presentation.Screen
import com.wahyusembiring.habit.core.presentation.composable.tab.PrimaryTab

fun NavGraphBuilder.overviewScreen(
   navController: NavHostController
) {
   composable<Screen.Overview> {
      val overviewViewModel: OverviewViewModel = viewModel(
         factory = viewModelFactory {
            addInitializer(OverviewViewModel::class) {
               OverviewViewModel(App.appModule.homeworkRepository)
            }
            build()
         }
      )
      OverviewScreen(
         viewModel = overviewViewModel,
         navController = navController
      )
   }
}

@Composable
private fun OverviewScreen(
   navController: NavHostController,
   viewModel: OverviewViewModel
) {

   val state by viewModel.state.collectAsStateWithLifecycle()

   OverviewScreen(
      state = state,
      onUIEvent = { viewModel.onUIEvent(it) }
   )
}

@Composable
private fun OverviewScreen(
   modifier: Modifier = Modifier,
   state: OverviewScreenUIState,
   onUIEvent: (OverviewScreenUIEvent) -> Unit,
) {
   val activeTab = remember { mutableIntStateOf(0) }

   Column(
      modifier = Modifier.fillMaxSize()
   ) {
      PrimaryTab(
         selectedTabState = activeTab,
         titles = state.tabTitles
      )
      EmptyTaskPlaceholder()
   }
}

@Composable
private fun EmptyTaskPlaceholder() {
   Box(
      modifier = Modifier.fillMaxSize(),
      contentAlignment = Alignment.Center
   ) {
      Text(
         text = stringResource(R.string.you_dont_have_any_task_yet)
      )
   }
}

//@Composable
//private fun ListOfTaskColumn(
//   tasks: List<Task>,
//   onDeleteTaskRequest: (Task) -> Unit,
//   onTaskClicked: (Task) -> Unit
//) {
//   DeleteableColumnList(
//      items = tasks,
//      onItemDeleteRequest = onDeleteTaskRequest
//   ) {
//      TaskItem(
//         task = it,
//         onTaskClicked = onTaskClicked
//      )
//   }
//}
//
//@Composable
//private fun TaskItem(
//   task: Task,
//   onTaskClicked: (Task) -> Unit
//) {
//   ListItem(
//      modifier = Modifier.clickable {
//         onTaskClicked(task)
//      },
//      headlineContent = { Text(task.title) },
//      trailingContent = {
//         Box(
//            modifier = Modifier
//               .background(
//                  color = MaterialTheme.colorScheme.secondaryContainer,
//                  shape = RoundedCornerShape(50)
//               )
//               .padding(5.dp, 1.dp)
//         ) {
//            Text(
//               text = "Selesai",
//               style = MaterialTheme.typography.labelSmall
//            )
//         }
//      }
//   )
//}
