package com.wahyusembiring.habit.presentation.provider

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import kotlinx.coroutines.CoroutineScope

@Composable
fun ProvideCompositionLocal(
   snackbarHostState: SnackbarHostState,
   coroutineScope: CoroutineScope,
   content: @Composable () -> Unit
) {
   CompositionLocalProvider(
      LocalSnackbarManager provides SnackbarManagerImpl(
         snackbarHostState,
         coroutineScope
      ),
      content = content
   )
}