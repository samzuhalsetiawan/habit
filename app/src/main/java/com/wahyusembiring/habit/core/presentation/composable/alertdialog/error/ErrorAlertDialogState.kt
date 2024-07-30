package com.wahyusembiring.habit.core.presentation.composable.alertdialog.error

data class ErrorAlertDialogState(
   val message: String = "",
   val buttonText: String = "",
   val onButtonClicked: () -> Unit = {},
)
