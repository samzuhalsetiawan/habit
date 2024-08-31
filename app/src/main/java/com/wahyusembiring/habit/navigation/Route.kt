package com.wahyusembiring.habit.navigation

import androidx.compose.material3.DrawerState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.wahyusembiring.calendar.CalendarScreen
import com.wahyusembiring.calendar.CalendarScreenViewModel
import com.wahyusembiring.common.navigation.Screen
import com.wahyusembiring.exam.ExamScreen
import com.wahyusembiring.exam.ExamScreenViewModel
import com.wahyusembiring.homework.CreateHomeworkScreen
import com.wahyusembiring.overview.OverviewScreen
import com.wahyusembiring.homework.CreateHomeworkScreenViewModel
import com.wahyusembiring.kanban.KanbanBoardScreen
import com.wahyusembiring.kanban.KanbanBoardScreenViewModel
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
            navController = navController
        )
    }
}

fun NavGraphBuilder.overviewScreen(
    navController: NavHostController,
    drawerState: DrawerState
) {
    composable<Screen.Overview> {
        val overviewViewModel: OverviewViewModel = hiltViewModel(it)
        OverviewScreen(
            viewModel = overviewViewModel,
            drawerState = drawerState,
            navController = navController
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
            navController = navController
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
            navController = navController
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
            navController = navController
        )
    }
}

fun NavGraphBuilder.kanbanBoardScreen(
    navController: NavHostController
) {
    composable<Screen.KanbanBoard> {
        val viewModel: KanbanBoardScreenViewModel = hiltViewModel(it)
        KanbanBoardScreen(
            viewModel = viewModel
        )
    }
}

fun NavGraphBuilder.calendarScreen(
    navController: NavHostController,
    drawerState: DrawerState
) {
    composable<Screen.Calendar> {
        val viewModel: CalendarScreenViewModel = hiltViewModel(it)
        CalendarScreen(
            viewModel = viewModel,
            navController = navController,
            drawerState = drawerState
        )
    }
}