package com.samzuhalsetiawan.habbits.ui.handler

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavHostController
import com.samzuhalsetiawan.habbits.App
import com.samzuhalsetiawan.habbits.MainViewModel
import com.samzuhalsetiawan.habbits.ui.screen.Screens
import com.samzuhalsetiawan.habbits.utils.provideViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

@Composable
fun LoginStateHandler(
   navController: NavHostController,
   lifecycleOwner: LifecycleOwner
) {

   val mainViewModel = provideViewModel {
      MainViewModel(App.repositoryModule.mainRepository)
   }

   LoginStateHandler(
      lifecycleOwner = lifecycleOwner,
      isLoggedFlow = mainViewModel.isLogin,
      onLoginStateChanged = { isLogged ->
         onLoginStateChanged(isLogged, navController, mainViewModel)
      }
   )

}

@Composable
private fun LoginStateHandler(
   lifecycleOwner: LifecycleOwner,
   isLoggedFlow: Flow<Boolean>,
   onLoginStateChanged: suspend (isLogged: Boolean) -> Unit
) {
   LaunchedEffect(key1 = isLoggedFlow) {
      lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
         isLoggedFlow.collect(onLoginStateChanged)
      }
   }
}

private suspend fun onLoginStateChanged(
   isLogged: Boolean,
   navController: NavHostController,
   mainViewModel: MainViewModel
) {
   if (isLogged) {
      navController.navigate(Screens.Jurnal) {
         popUpTo(navController.graph.id) { inclusive = true }
      }
   } else {
      navController.navigate(
         if (mainViewModel.isNewUser.first()) Screens.Welcome else Screens.Login
      ) {
         popUpTo(navController.graph.id) { inclusive = true }
      }
   }
}