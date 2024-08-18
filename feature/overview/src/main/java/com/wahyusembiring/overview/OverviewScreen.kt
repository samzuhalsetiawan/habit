package com.wahyusembiring.overview

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.wahyusembiring.ui.component.tab.PrimaryTab


@Composable
fun OverviewScreen(
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
