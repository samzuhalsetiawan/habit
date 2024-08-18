package com.wahyusembiring.navigation.component.floatingactionbutton

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.wahyusembiring.navigation.Screen


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
