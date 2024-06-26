package com.samzuhalsetiawan.habbits.ui.handler

import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.samzuhalsetiawan.habbits.ui.localcomposition.provider.LocalSnackbarController

@Composable
fun SnackbarHandler(
   message: String?,
   onSnackbarResult: (SnackbarResult) -> Unit,
) {
   val snackbarController = LocalSnackbarController.current

   LaunchedEffect(key1 = message) {
      if (message == null) return@LaunchedEffect
      snackbarController.showSnackbar(
         message = message,
         actionLabel = null,
         withDismissAction = false,
         duration = SnackbarDuration.Short,
         onSnackbarResult = onSnackbarResult
      )
   }
}