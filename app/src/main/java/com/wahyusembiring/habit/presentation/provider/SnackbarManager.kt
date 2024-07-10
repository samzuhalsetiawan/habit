package com.wahyusembiring.habit.presentation.provider

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.remember
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

interface SnackbarManager {
   fun showSnackbar(message: String)
}

class SnackbarManagerImpl(
   private val snackbarHostState: SnackbarHostState,
   private val coroutineScope: CoroutineScope
) : SnackbarManager {
   override fun showSnackbar(message: String) {
      coroutineScope.launch {
         snackbarHostState.showSnackbar(message)
      }
   }
}

val LocalSnackbarManager = compositionLocalOf<SnackbarManager> {
   error("No SnackbarManager provided")
}

@Composable
fun rememberSanckbarHostState() = remember { SnackbarHostState() }