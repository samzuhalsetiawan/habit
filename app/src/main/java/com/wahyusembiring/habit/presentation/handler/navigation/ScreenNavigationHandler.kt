package com.wahyusembiring.habit.presentation.handler.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.wahyusembiring.habit.presentation.screen.Screen
import com.wahyusembiring.habit.presentation.screen.home.HomeScreen

@Composable
fun ScreenNavigationHandler(
   navController: NavHostController,
   scaffoldPadding: PaddingValues
) {
   NavHost(
      navController = navController,
      startDestination = Screen.Home,
      modifier = Modifier.padding(scaffoldPadding)
   ) {
      composable<Screen.Home> {
         HomeScreen(navController = navController)
      }
   }
}