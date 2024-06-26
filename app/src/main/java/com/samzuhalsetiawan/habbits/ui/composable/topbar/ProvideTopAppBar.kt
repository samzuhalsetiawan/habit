package com.samzuhalsetiawan.habbits.ui.composable.topbar

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.toRoute
import com.samzuhalsetiawan.habbits.App
import com.samzuhalsetiawan.habbits.MainActivity
import com.samzuhalsetiawan.habbits.MainViewModel
import com.samzuhalsetiawan.habbits.ui.localcomposition.provider.LocalPopUpController
import com.samzuhalsetiawan.habbits.ui.localcomposition.provider.PopUpDialog
import com.samzuhalsetiawan.habbits.ui.screen.Screens
import com.samzuhalsetiawan.habbits.ui.screen.menu_add_habit.MenuAddHabitTopAppBar
import com.samzuhalsetiawan.habbits.ui.screen.menu_add_habit.MenuAddHabitViewModel
import com.samzuhalsetiawan.habbits.ui.screen.menu_jurnal.MenuJurnalTopAppBar
import com.samzuhalsetiawan.habbits.ui.screen.menu_progress.MenuProgressTopAppBar
import com.samzuhalsetiawan.habbits.utils.currentRoute
import com.samzuhalsetiawan.habbits.utils.provideViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProvideTopAppBar(
   scrollBehavior: TopAppBarScrollBehavior,
   navBackStackEntry: NavBackStackEntry?,
) {

   when (navBackStackEntry.currentRoute) {
      Screens.Jurnal::class.simpleName -> MenuJurnalTopAppBar(scrollBehavior = scrollBehavior)
      Screens.Progress::class.simpleName -> MenuProgressTopAppBar(scrollBehavior = scrollBehavior)
      Screens.AddHabit::class.simpleName -> MenuAddHabitTopAppBar()
      else -> return
   }

}