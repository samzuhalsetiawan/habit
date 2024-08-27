package com.wahyusembiring.navigation.component.floatingactionbutton

import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import com.wahyusembiring.navigation.Screen
import com.wahyusembiring.navigation.util.routeSimpleClassName


@Composable
fun FloatingActionButton(
//   TODO: Look at now in android app github to see how they distinguish between screen
    navBackStackEntry: NavBackStackEntry?,
    navController: NavHostController,
) {
    when (navBackStackEntry.routeSimpleClassName) {
        Screen.Overview::class.simpleName -> HomeworkExamAndReminderFAB(navController = navController)
        else -> Unit
    }
}
