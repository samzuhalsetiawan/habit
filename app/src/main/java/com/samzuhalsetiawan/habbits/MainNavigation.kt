package com.samzuhalsetiawan.habbits

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.samzuhalsetiawan.habbits.repository.MainRepository
import com.samzuhalsetiawan.habbits.ui.screen.Screens
import com.samzuhalsetiawan.habbits.ui.screen.menu_add_habit.MenuAddHabit
import com.samzuhalsetiawan.habbits.ui.screen.menu_detail.MenuDetailHabit
import com.samzuhalsetiawan.habbits.ui.screen.menu_jurnal.MenuJurnal
import com.samzuhalsetiawan.habbits.ui.screen.menu_login.MenuLogin
import com.samzuhalsetiawan.habbits.ui.screen.menu_pengaturan.MenuPengaturan
import com.samzuhalsetiawan.habbits.ui.screen.menu_pertanyaan.MenuPertanyaan
import com.samzuhalsetiawan.habbits.ui.screen.menu_progress.MenuProgress
import com.samzuhalsetiawan.habbits.ui.screen.menu_register.MenuRegister
import com.samzuhalsetiawan.habbits.ui.screen.menu_result.MenuResult
import com.samzuhalsetiawan.habbits.ui.screen.menu_welcome.MenuWelcome

@Composable
fun MainNavigation(
   navController: NavHostController,
   startDestination: Screens,
   modifier: Modifier = Modifier,
) {

   NavHost(
      navController = navController,
      startDestination = startDestination,
      enterTransition = { EnterTransition.None },
      exitTransition = { ExitTransition.None },
      modifier = modifier
   ) {

      composable<Screens.Welcome> {
         MenuWelcome()
      }
      composable<Screens.Pertanyaan> {
         MenuPertanyaan()
      }
      composable<Screens.Result> {
         MenuResult()
      }
      composable<Screens.Login> {
         MenuLogin()
      }
      composable<Screens.Register> {
         MenuRegister()
      }
      composable<Screens.Jurnal> {
         MenuJurnal()
      }
      composable<Screens.Progress> {
         MenuProgress()
      }
      composable<Screens.Pengaturan> {
         MenuPengaturan()
      }
      composable<Screens.AddHabit> {
         MenuAddHabit()
      }
      composable<Screens.DetailHabit> {
         val habitId = it.toRoute<Screens.DetailHabit>().habitId
         MenuDetailHabit(habitId)
      }
   }

}