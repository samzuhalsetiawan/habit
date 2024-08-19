package com.wahyusembiring.habit

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.wahyusembiring.habit.navigation.createHomeworkScreen
import com.wahyusembiring.habit.navigation.createReminderScreen
import com.wahyusembiring.habit.navigation.createSubjectScreen
import com.wahyusembiring.habit.navigation.examScreen
import com.wahyusembiring.habit.navigation.overviewScreen
import com.wahyusembiring.navigation.MainNavigation
import com.wahyusembiring.navigation.Screen
import com.wahyusembiring.ui.component.navigationdrawer.DrawerItem
import com.wahyusembiring.ui.component.navigationdrawer.NavigationDrawer
import com.wahyusembiring.navigation.component.floatingactionbutton.FloatingActionButton
import com.wahyusembiring.navigation.util.routeSimpleClassName
import com.wahyusembiring.ui.component.topappbar.TopAppBar
import com.wahyusembiring.ui.component.navigationdrawer.rememberNavigationDrawerState
import com.wahyusembiring.ui.theme.HabitTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      enableEdgeToEdge()
      setContent {
         HabitTheme {
            val coroutineScope = rememberCoroutineScope()
            val navController = rememberNavController()
            val navigationDrawerState = rememberNavigationDrawerState()
            val navBackStackEntry by navController.currentBackStackEntryAsState()

            NavigationDrawer(
               drawerItems = DrawerItem.defaultItems,
               navigationDrawerState = navigationDrawerState
            ) {
               Scaffold(
                  topBar = {
                     val title = when (navBackStackEntry?.routeSimpleClassName) {
                        Screen.Overview::class.simpleName -> stringResource(R.string.overview)
                        else -> null
                     }
                     if (title != null) {
                        TopAppBar(
                           title = title,
                           onMenuClick = { coroutineScope.launch { navigationDrawerState.materialDrawerState.open() } }
                        )
                     }
                  },
                  floatingActionButton = {
                     FloatingActionButton(
                        routeSimpleClassName = navBackStackEntry?.routeSimpleClassName,
                        navController = navController
                     )
                  },
               ) { scaffoldPadding ->
                  MainNavigation(
                     navController = navController,
                     scaffoldPadding = scaffoldPadding
                  ) {
                     createHomeworkScreen(navController)
                     overviewScreen(navController)
                     createSubjectScreen(navController)
                     examScreen(navController)
                     createReminderScreen(navController)
                  }
               }
            }
         }
      }
   }
}