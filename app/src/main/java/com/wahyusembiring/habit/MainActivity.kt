package com.wahyusembiring.habit

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.getValue
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.wahyusembiring.habit.navigation.createHomeworkScreen
import com.wahyusembiring.habit.navigation.createReminderScreen
import com.wahyusembiring.habit.navigation.createSubjectScreen
import com.wahyusembiring.habit.navigation.examScreen
import com.wahyusembiring.habit.navigation.kanbanBoardScreen
import com.wahyusembiring.habit.navigation.overviewScreen
import com.wahyusembiring.habit.scaffold.MainScaffold
import com.wahyusembiring.ui.theme.HabitTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HabitTheme {
                val navController = rememberNavController()
                val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

                MainScaffold(
                    navController = navController,
                    drawerState = drawerState,
                    screens = {
                        createHomeworkScreen(navController)
                        overviewScreen(navController, drawerState)
                        createSubjectScreen(navController)
                        examScreen(navController)
                        createReminderScreen(navController)
                        kanbanBoardScreen(navController)
                    }
                )
            }
        }
    }
}