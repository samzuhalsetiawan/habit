package com.wahyusembiring.habit.presentation

import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.wahyusembiring.habit.presentation.composable.drawer.DrawerItem
import com.wahyusembiring.habit.presentation.composable.drawer.MainNavigationDrawer
import com.wahyusembiring.habit.presentation.composable.fab.FAB
import com.wahyusembiring.habit.presentation.composable.fab.MultiFloatingActionButton
import com.wahyusembiring.habit.presentation.composable.topappbar.TopAppBar
import com.wahyusembiring.habit.presentation.handler.navigation.ScreenNavigationHandler
import com.wahyusembiring.habit.presentation.provider.ProvideCompositionLocal
import com.wahyusembiring.habit.presentation.provider.rememberSanckbarHostState
import kotlinx.coroutines.launch

@Composable
fun MainActivityContent() {
   val snackbarHostState = rememberSanckbarHostState()
   val coroutineScope = rememberCoroutineScope()
   val navController = rememberNavController()
   val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
   val navBackStackEntry by navController.currentBackStackEntryAsState()

   ProvideCompositionLocal(
      snackbarHostState = snackbarHostState,
      coroutineScope = coroutineScope
   ) {
      MainNavigationDrawer(
         drawerItems = DrawerItem.defaultItems,
         drawerState = drawerState
      ) {
         Scaffold(
            topBar = {
               TopAppBar(
                  navBackStackEntry = navBackStackEntry,
                  onMenuClick = { coroutineScope.launch { drawerState.open() } }
               )
            },
            snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
         ) { scaffoldPadding ->
            ScreenNavigationHandler(
               navController = navController,
               scaffoldPadding = scaffoldPadding
            )
         }
      }
   }
}