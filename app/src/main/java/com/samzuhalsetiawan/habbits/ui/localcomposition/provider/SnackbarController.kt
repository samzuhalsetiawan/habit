package com.samzuhalsetiawan.habbits.ui.localcomposition.provider

import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.staticCompositionLocalOf
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

interface SnackbarController {

   fun showSnackbar(
      message: String,
      actionLabel: String?,
      withDismissAction: Boolean,
      duration: SnackbarDuration,
      onSnackbarResult: (SnackbarResult) -> Unit
   )

}

class SnackbarControllerImpl(
   private val snackbarHostState: SnackbarHostState,
   private val coroutineScope: CoroutineScope
): SnackbarController {

   override fun showSnackbar(
      message: String,
      actionLabel: String?,
      withDismissAction: Boolean,
      duration: SnackbarDuration,
      onSnackbarResult: (SnackbarResult) -> Unit
   ) {
      coroutineScope.launch {
         snackbarHostState.showSnackbar(
            message = message,
            actionLabel = actionLabel,
            withDismissAction = withDismissAction,
            duration = duration
         ).let(onSnackbarResult)
      }
   }

}

val LocalSnackbarController = staticCompositionLocalOf<SnackbarController> {
   error("No LocalSnackbarController Provided")
}