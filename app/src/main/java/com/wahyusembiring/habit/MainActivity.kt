package com.wahyusembiring.habit

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.getValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.wahyusembiring.habit.navigation.calendarScreen
import com.wahyusembiring.habit.navigation.createHomeworkScreen
import com.wahyusembiring.habit.navigation.createReminderScreen
import com.wahyusembiring.habit.navigation.createSubjectScreen
import com.wahyusembiring.habit.navigation.examScreen
import com.wahyusembiring.habit.navigation.gradesScreen
import com.wahyusembiring.habit.navigation.kanbanBoardScreen
import com.wahyusembiring.habit.navigation.onBoardingScreen
import com.wahyusembiring.habit.navigation.overviewScreen
import com.wahyusembiring.habit.navigation.thesisPlannerScreen
import com.wahyusembiring.habit.navigation.thesisSelectionScreen
import com.wahyusembiring.habit.scaffold.MainScaffold
import com.wahyusembiring.ui.theme.HabitTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        splashScreen.setKeepOnScreenCondition { viewModel.isAppReady.value }
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HabitTheme {
                val navController = rememberNavController()
                val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

                MainScaffold(
                    mainViewModel = viewModel,
                    navController = navController,
                    drawerState = drawerState,
                    screens = {
                        createHomeworkScreen(navController)
                        overviewScreen(navController, drawerState)
                        createSubjectScreen(navController)
                        examScreen(navController)
                        createReminderScreen(navController)
                        kanbanBoardScreen(navController)
                        calendarScreen(navController, drawerState)
                        onBoardingScreen(navController)
                        thesisSelectionScreen(navController, drawerState)
                        thesisPlannerScreen(navController, drawerState)
                        gradesScreen(navController, drawerState)
                    }
                )
            }
        }
    }
}