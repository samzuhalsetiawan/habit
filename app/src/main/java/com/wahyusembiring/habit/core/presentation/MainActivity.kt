package com.wahyusembiring.habit.core.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.wahyusembiring.habit.navigation.MainNavigation
import com.wahyusembiring.habit.core.presentation.composable.navigationdrawer.DrawerItem
import com.wahyusembiring.habit.core.presentation.composable.navigationdrawer.NavigationDrawer
import com.wahyusembiring.habit.core.presentation.composable.floatingactionbutton.FloatingActionButton
import com.wahyusembiring.habit.core.presentation.composable.topappbar.TopAppBar
import com.wahyusembiring.habit.core.domain.util.GetterUtil.routeSimpleClassName
import com.wahyusembiring.habit.core.presentation.composable.navigationdrawer.rememberNavigationDrawerState
import com.wahyusembiring.habit.theme.HabitTheme
import kotlinx.coroutines.launch

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
                     TopAppBar(
                        navBackStackEntry = navBackStackEntry,
                        onMenuClick = { coroutineScope.launch { navigationDrawerState.materialDrawerState.open() } }
                     )
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
                  )
               }
            }
         }
      }
   }
}