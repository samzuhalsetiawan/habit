package com.wahyusembiring.habit.presentation.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.dp

object Spacing {
   val Large = 24.dp
   val Medium = 16.dp
   val Small = 8.dp
}

val LocalSpacing = compositionLocalOf { Spacing }

val MaterialTheme.spacing: Spacing
   @Composable
   get() = LocalSpacing.current