package com.wahyusembiring.habit.core.presentation.composable.alertdialog.information

data class InformationAlertDialogState(
   val title: String = "",
   val message: String = "",
   val buttonText: String = "",
   val onButtonClicked: () -> Unit = {}
)
