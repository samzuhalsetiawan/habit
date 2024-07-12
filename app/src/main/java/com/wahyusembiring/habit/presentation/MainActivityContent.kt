package com.wahyusembiring.habit.presentation

import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.wahyusembiring.habit.domain.utils.GetterUtil.routeSimpleClassName
import com.wahyusembiring.habit.presentation.composable.drawer.DrawerItem
import com.wahyusembiring.habit.presentation.composable.drawer.MainNavigationDrawer
import com.wahyusembiring.habit.presentation.composable.fab.ClickedFAB
import com.wahyusembiring.habit.presentation.composable.fab.ProvideFloatingActionButton
import com.wahyusembiring.habit.presentation.composable.topappbar.TopAppBar
import com.wahyusembiring.habit.presentation.handler.floatingactionbutton.FloatingActionButtonClickHandler
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
   val clickedFABState = remember { mutableStateOf<ClickedFAB?>(null) }

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
            floatingActionButton = {
               ProvideFloatingActionButton(
                  routeSimpleClassName = navBackStackEntry.routeSimpleClassName,
                  onClick = { clickedFABState.value = it }
               )
            },
            snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
         ) { scaffoldPadding ->
            ScreenNavigationHandler(
               navController = navController,
               scaffoldPadding = scaffoldPadding
            )
            FloatingActionButtonClickHandler(clickedFABState = clickedFABState)
         }
      }
   }
}
