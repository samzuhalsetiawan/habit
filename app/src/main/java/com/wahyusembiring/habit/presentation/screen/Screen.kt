package com.wahyusembiring.habit.presentation.screen

import kotlinx.serialization.Serializable

@Serializable
sealed class Screen {
   @Serializable
   data object Home : Screen()
}