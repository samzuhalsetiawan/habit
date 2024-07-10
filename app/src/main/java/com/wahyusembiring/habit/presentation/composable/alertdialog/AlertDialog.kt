package com.wahyusembiring.habit.presentation.composable.alertdialog

sealed class AlertDialog {
   data class Confirm(
      val title: String,
      val message: String,
      val positiveButtonText: String,
      val negativeButtonText: String,
      val onPositiveButtonClick: () -> Unit,
      val onNegativeButtonClick: () -> Unit
   ) : AlertDialog()

   data class Inform(
      val title: String,
      val message: String,
      val buttonText: String,
      val onButtonClicked: () -> Unit
   ) : AlertDialog()

   data class Loading(
      val message: String
   ) : AlertDialog()

   data object Error : AlertDialog()
}