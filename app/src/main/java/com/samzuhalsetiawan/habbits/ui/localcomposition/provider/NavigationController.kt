package com.samzuhalsetiawan.habbits.ui.localcomposition.provider

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.navigation.NavHostController
import com.samzuhalsetiawan.habbits.ui.screen.Screens

interface NavigationController {

   fun navigate(route: Screens)
   fun navigateWithPopUpTo(
      route: Screens,
      popUpToRoute: Screens = route,
      inclusive: Boolean = false
   )
   fun popUpAndLunchInSingleTop(route: Screens)
   fun popBackStack()

}

class NavigationControllerImpl(
   private val navController: NavHostController
) : NavigationController {

   override fun navigate(route: Screens) {
      navController.navigate(route)
   }

   override fun navigateWithPopUpTo(route: Screens, popUpToRoute: Screens, inclusive: Boolean) {
      navController.navigate(route) {
         popUpTo(popUpToRoute) {
            this.inclusive = inclusive
         }
      }
   }

   override fun popUpAndLunchInSingleTop(route: Screens) {
      navController.navigate(route) {
         popUpTo(route)
         launchSingleTop = true
      }
   }

   override fun popBackStack() {
      navController.popBackStack()
   }
}

val LocalNavigationController = staticCompositionLocalOf<NavigationController> {
   error("No LocalNavigationController Provided")
}