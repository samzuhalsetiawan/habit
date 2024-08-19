package com.wahyusembiring.habit.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.wahyusembiring.exam.ExamScreen
import com.wahyusembiring.exam.ExamScreenViewModel
import com.wahyusembiring.homework.CreateHomeworkScreen
import com.wahyusembiring.overview.OverviewScreen
import com.wahyusembiring.homework.CreateHomeworkScreenViewModel
import com.wahyusembiring.navigation.Screen
import com.wahyusembiring.overview.OverviewViewModel
import com.wahyusembiring.reminder.CreateReminderScreen
import com.wahyusembiring.reminder.CreateReminderScreenViewModel
import com.wahyusembiring.subject.CreateSubjectScreen
import com.wahyusembiring.subject.CreateSubjectViewModel

fun NavGraphBuilder.createHomeworkScreen(
   navController: NavHostController
) {
   composable<Screen.CreateHomework> {
      val viewModel: CreateHomeworkScreenViewModel = hiltViewModel(it)
      CreateHomeworkScreen(
         viewModel = viewModel,
         navigateBack = { navController.popBackStack() },
         navigateToCreateSubjectScreen = { navController.navigate(Screen.CreateSubject) }
      )
   }
}

fun NavGraphBuilder.overviewScreen(
   navController: NavHostController
) {
   composable<Screen.Overview> {
      val overviewViewModel: OverviewViewModel = hiltViewModel(it)
      OverviewScreen(
         viewModel = overviewViewModel
      )
   }
}

fun NavGraphBuilder.createSubjectScreen(
   navController: NavHostController
) {
   composable<Screen.CreateSubject> {
      val viewModel: CreateSubjectViewModel = hiltViewModel(it)
      CreateSubjectScreen(
         viewModel = viewModel,
         onNavigateBack = { navController.popBackStack() }
      )
   }
}

fun NavGraphBuilder.examScreen(
   navController: NavHostController
) {
   composable<Screen.CreateExam> {
      val viewModel: ExamScreenViewModel = hiltViewModel(it)
      ExamScreen(
         viewModel = viewModel,
         navigateBack = { navController.popBackStack() }
      )
   }
}

fun NavGraphBuilder.createReminderScreen(
   navController: NavHostController
) {
   composable<Screen.CreateReminder> {
      val viewModel: CreateReminderScreenViewModel = hiltViewModel(it)
      CreateReminderScreen(
         viewModel = viewModel,
         navigateBack = { navController.popBackStack() }
      )
   }
}