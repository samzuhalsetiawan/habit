package com.samzuhalsetiawan.habbits.ui.localcomposition

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.navigation.NavHostController
import com.samzuhalsetiawan.habbits.ui.localcomposition.provider.LocalNavigationController
import com.samzuhalsetiawan.habbits.ui.localcomposition.provider.LocalPopUpController
import com.samzuhalsetiawan.habbits.ui.localcomposition.provider.LocalSnackbarController
import com.samzuhalsetiawan.habbits.ui.localcomposition.provider.NavigationControllerImpl
import com.samzuhalsetiawan.habbits.ui.localcomposition.provider.PopUpControllerImpl
import com.samzuhalsetiawan.habbits.ui.localcomposition.provider.PopUpManager
import com.samzuhalsetiawan.habbits.ui.localcomposition.provider.SnackbarControllerImpl
import kotlinx.coroutines.CoroutineScope

@Composable
fun ProvideLocalComposition(
   snackbarHostState: SnackbarHostState,
   coroutineScope: CoroutineScope,
   popUpManager: PopUpManager,
   navController: NavHostController,
   content: @Composable () -> Unit
) {

   CompositionLocalProvider(
      LocalSnackbarController provides SnackbarControllerImpl(snackbarHostState, coroutineScope),
      LocalPopUpController provides PopUpControllerImpl(popUpManager),
      LocalNavigationController provides NavigationControllerImpl(navController),
      content = content
   )
}