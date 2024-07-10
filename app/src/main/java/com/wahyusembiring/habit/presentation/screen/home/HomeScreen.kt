package com.wahyusembiring.habit.presentation.screen.home

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.wahyusembiring.habit.R
import com.wahyusembiring.habit.domain.model.Task
import com.wahyusembiring.habit.presentation.composable.alertdialog.AlertDialog
import com.wahyusembiring.habit.presentation.composable.lists.DeleteableColumnList
import com.wahyusembiring.habit.presentation.composable.tab.PrimaryTab
import com.wahyusembiring.habit.presentation.composable.tab.rememberTabState
import com.wahyusembiring.habit.presentation.handler.alertdialog.ScreenAlertDialogHandler

@Composable
fun HomeScreen(
   navController: NavController,
) {
   val homeViewModel: HomeViewModel = viewModel()
   val homeState by homeViewModel.state.collectAsStateWithLifecycle()

   HomeScreen(
      state = homeState,
      userAction = homeViewModel
   )
}

@Composable
private fun HomeScreen(
   modifier: Modifier = Modifier,
   state: HomeScreenState,
   userAction: HomeUserAction
) {
   val tabState = rememberTabState()
   val tabTitles = stringArrayResource(id = R.array.home_screen_tabs)

   Column(
      modifier = modifier.fillMaxSize()
   ) {
      PrimaryTab(
         tabState = tabState,
         titles = tabTitles.toList()
      )
      when {
         state.tasks.isNotEmpty() -> {
            ListOfTaskColumn(
               tasks = state.tasks,
               onTaskClicked = userAction::onTaskClicked,
               onDeleteTaskRequest = userAction::onDeleteTaskRequest,
            )
         }

         state.alertDialog is AlertDialog.Loading -> Unit
         else -> EmptyTaskPlaceholder()
      }
   }
   ScreenAlertDialogHandler(
      dialog = state.alertDialog,
      onDismissRequest = { userAction.dissmissAlertDialog() }
   )
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

@Composable
private fun ListOfTaskColumn(
   tasks: List<Task>,
   onDeleteTaskRequest: (Task) -> Unit,
   onTaskClicked: (Task) -> Unit
) {
   DeleteableColumnList(
      items = tasks,
      onItemDeleteRequest = onDeleteTaskRequest
   ) {
      TaskItem(
         task = it,
         onTaskClicked = onTaskClicked
      )
   }
}

@Composable
private fun TaskItem(
   task: Task,
   onTaskClicked: (Task) -> Unit
) {
   ListItem(
      modifier = Modifier.clickable {
         onTaskClicked(task)
      },
      headlineContent = { Text(task.title) },
      trailingContent = {
         Box(
            modifier = Modifier
               .background(
                  color = MaterialTheme.colorScheme.secondaryContainer,
                  shape = RoundedCornerShape(50)
               )
               .padding(5.dp, 1.dp)
         ) {
            Text(
               text = "Selesai",
               style = MaterialTheme.typography.labelSmall
            )
         }
      }
   )
}

data class HomeScreenState(
   val tasks: List<Task> = emptyList(),
   val alertDialog: AlertDialog? = null
)

interface HomeUserAction {
   fun onTaskClicked(task: Task)
   fun onDeleteTaskRequest(task: Task)
   fun dissmissAlertDialog()
}
