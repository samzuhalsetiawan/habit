package com.wahyusembiring.navigation.component.floatingactionbutton

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import com.wahyusembiring.navigation.R
import com.wahyusembiring.navigation.Screen
import com.wahyusembiring.navigation.component.floatingactionbutton.component.MultiFloatingActionButton
import com.wahyusembiring.navigation.component.floatingactionbutton.component.Scrim


enum class ClickedFAB {
   REMINDER, EXAM, HOMEWORK
}

@Composable
fun HomeworkExamAndReminderFAB(
   navController: NavHostController,
) {

   HomeworkExamAndReminderFAB {
      when (it) {
         ClickedFAB.REMINDER -> Unit
         ClickedFAB.EXAM -> navController.navigate(Screen.CreateExam)
         ClickedFAB.HOMEWORK -> navController.navigate(Screen.CreateHomework)
      }
   }
}

@Composable
private fun HomeworkExamAndReminderFAB(
   onFabClick: (fab: ClickedFAB) -> Unit
) {
   var isExpanded by remember { mutableStateOf(false) }

   Scrim(isVisible = isExpanded)
   MultiFloatingActionButton(
      mainFloatingActionButton = {
         MainFloatingActionButton(
            onClick = { isExpanded = !isExpanded },
            isExpanded = isExpanded
         )
      },
      subFloatingActionButton = {
         SubFloatingActionButton(
            isExpanded = isExpanded,
            onFabClick = {
               onFabClick(it)
               isExpanded = false
            }
         )
      }
   )
}

@Composable
private fun MainFloatingActionButton(
   modifier: Modifier = Modifier,
   onClick: () -> Unit,
   isExpanded: Boolean
) {
   FloatingActionButton(
      modifier = modifier,
      onClick = onClick
   ) {
      val animatedDegree by animateFloatAsState(
         label = "Icon Rotation Animation",
         targetValue = if (isExpanded) 135f else 0f,
      )
      Icon(
         modifier = Modifier.rotate(animatedDegree),
         painter = painterResource(id = R.drawable.ic_add),
         contentDescription = stringResource(R.string.create_task)
      )
   }
}

@Composable
private fun ColumnScope.SubFloatingActionButton(
   modifier: Modifier = Modifier,
   isExpanded: Boolean,
   onFabClick: (subFab: ClickedFAB) -> Unit,
) {
   com.wahyusembiring.navigation.component.floatingactionbutton.component.SubFloatingActionButton(
      isVisible = isExpanded,
      onClick = { onFabClick(ClickedFAB.REMINDER) },
   ) {
      Icon(
         painter = painterResource(id = R.drawable.ic_reminder),
         contentDescription = stringResource(R.string.add_reminder)
      )
   }
   com.wahyusembiring.navigation.component.floatingactionbutton.component.SubFloatingActionButton(
      isVisible = isExpanded,
      onClick = { onFabClick(ClickedFAB.EXAM) },
   ) {
      Icon(
         painter = painterResource(id = R.drawable.ic_exam),
         contentDescription = stringResource(R.string.add_exam_schedule)
      )
   }
   com.wahyusembiring.navigation.component.floatingactionbutton.component.SubFloatingActionButton(
      isVisible = isExpanded,
      onClick = { onFabClick(ClickedFAB.HOMEWORK) },
   ) {
      Icon(
         painter = painterResource(id = R.drawable.ic_homework),
         contentDescription = stringResource(R.string.add_homework)
      )
   }
}