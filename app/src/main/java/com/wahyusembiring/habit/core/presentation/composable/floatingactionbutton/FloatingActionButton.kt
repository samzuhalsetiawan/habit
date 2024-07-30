package com.wahyusembiring.habit.core.presentation.composable.floatingactionbutton

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.wahyusembiring.habit.core.presentation.Screen
import com.wahyusembiring.habit.feature.overview.presentation.component.floatingactionbutton.HomeworkExamAndReminderFAB


@Composable
fun FloatingActionButton(
//   TODO: Look at now in android app github to see how they distinguish between screen
   routeSimpleClassName: String?,
   navController: NavHostController,
) {
   when (routeSimpleClassName) {
      Screen.Overview::class.simpleName -> HomeworkExamAndReminderFAB(navController = navController)
      else -> Unit
   }
}
