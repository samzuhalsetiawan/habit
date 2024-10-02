package com.wahyusembiring.habit.navigation

import androidx.compose.material3.DrawerState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.wahyusembiring.calendar.CalendarScreen
import com.wahyusembiring.calendar.CalendarScreenViewModel
import com.wahyusembiring.common.navigation.Screen
import com.wahyusembiring.exam.ExamScreen
import com.wahyusembiring.exam.ExamScreenViewModel
import com.wahyusembiring.grades.GradesScreen
import com.wahyusembiring.grades.GradesScreenViewModel
import com.wahyusembiring.homework.CreateHomeworkScreen
import com.wahyusembiring.overview.OverviewScreen
import com.wahyusembiring.homework.CreateHomeworkScreenViewModel
import com.wahyusembiring.kanban.KanbanBoardScreen
import com.wahyusembiring.kanban.KanbanBoardScreenViewModel
import com.wahyusembiring.onboarding.OnBoardingScreen
import com.wahyusembiring.onboarding.OnBoardingScreenViewModel
import com.wahyusembiring.overview.OverviewViewModel
import com.wahyusembiring.reminder.CreateReminderScreen
import com.wahyusembiring.reminder.CreateReminderScreenViewModel
import com.wahyusembiring.subject.CreateSubjectScreen
import com.wahyusembiring.subject.CreateSubjectViewModel
import com.wahyusembiring.thesisplanner.screen.planner.ThesisPlannerScreen
import com.wahyusembiring.thesisplanner.screen.planner.ThesisPlannerScreenViewModel
import com.wahyusembiring.thesisplanner.screen.thesisselection.ThesisSelectionScreen
import com.wahyusembiring.thesisplanner.screen.thesisselection.ThesisSelectionScreenViewModel

fun NavGraphBuilder.createHomeworkScreen(
    navController: NavHostController
) {
    composable<Screen.CreateHomework> {
        val route = it.toRoute<Screen.CreateHomework>()

        val viewModel: CreateHomeworkScreenViewModel = hiltViewModel(
            viewModelStoreOwner = it,
            creationCallback = { factory: CreateHomeworkScreenViewModel.Factory ->
                factory.create(route.homeworkId)
            }
        )
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
        val route = it.toRoute<Screen.CreateExam>()
        val viewModel: ExamScreenViewModel = hiltViewModel(
            viewModelStoreOwner = it,
            creationCallback = { factory: ExamScreenViewModel.Factory ->
                factory.create(route.examId)
            }
        )
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
        val route = it.toRoute<Screen.CreateReminder>()
        val viewModel: CreateReminderScreenViewModel = hiltViewModel(
            viewModelStoreOwner = it,
            creationCallback = { factory: CreateReminderScreenViewModel.Factory ->
                factory.create(route.reminderId)
            }
        )
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

fun NavGraphBuilder.onBoardingScreen(
    navController: NavHostController
) {
    composable<Screen.OnBoarding> {
        val viewModel: OnBoardingScreenViewModel = hiltViewModel(it)
        OnBoardingScreen(
            viewModel = viewModel,
            navController = navController
        )
    }
}

fun NavGraphBuilder.thesisSelectionScreen(
    navController: NavHostController,
    drawerState: DrawerState
) {
    composable<Screen.ThesisSelection> {
        val viewModel: ThesisSelectionScreenViewModel = hiltViewModel(it)
        ThesisSelectionScreen(
            viewModel = viewModel,
            drawerState = drawerState,
            navController = navController
        )
    }
}


fun NavGraphBuilder.thesisPlannerScreen(
    navController: NavHostController,
    drawerState: DrawerState
) {
    composable<Screen.ThesisPlanner> {
        val thesisId = it.toRoute<Screen.ThesisPlanner>().thesisId

        val viewModel: ThesisPlannerScreenViewModel = hiltViewModel(
            viewModelStoreOwner = it,
            creationCallback = { factory: ThesisPlannerScreenViewModel.Factory ->
                factory.create(thesisId)
            }
        )
        ThesisPlannerScreen(
            viewModel = viewModel,
            drawerState = drawerState,
            navController = navController
        )
    }
}

fun NavGraphBuilder.gradesScreen(
    navController: NavHostController,
    drawerState: DrawerState
) {
    composable<Screen.Grades> {
        val viewModel: GradesScreenViewModel = hiltViewModel(it)
        GradesScreen(
            viewModel = viewModel,
            navController = navController,
            drawerState = drawerState
        )
    }
}