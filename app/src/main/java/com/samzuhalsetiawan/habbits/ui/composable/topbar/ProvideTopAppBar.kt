package com.samzuhalsetiawan.habbits.ui.composable.topbar

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import com.samzuhalsetiawan.habbits.ui.screen.Screens
import com.samzuhalsetiawan.habbits.ui.screen.menu_add_habit.MenuAddHabitTopAppBar
import com.samzuhalsetiawan.habbits.ui.screen.menu_jurnal.MenuJurnalTopAppBar
import com.samzuhalsetiawan.habbits.ui.screen.menu_progress.MenuProgressTopAppBar
import com.samzuhalsetiawan.habbits.utils.currentRoute

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProvideTopAppBar(
   scrollBehavior: TopAppBarScrollBehavior,
   navBackStackEntry: NavBackStackEntry?,
) {

   when (navBackStackEntry.currentRoute) {
      Screens.Jurnal::class.simpleName -> MenuJurnalTopAppBar(
         scrollBehavior = scrollBehavior,
         navBackStackEntry = navBackStackEntry
      )

      Screens.Progress::class.simpleName -> MenuProgressTopAppBar(
         scrollBehavior = scrollBehavior,
         navBackStackEntry = navBackStackEntry
      )

      Screens.AddHabit::class.simpleName -> MenuAddHabitTopAppBar(navBackStackEntry = navBackStackEntry)
      else -> return
   }

}