package com.wahyusembiring.habit.presentation.handler.alertdialog

import androidx.compose.runtime.Composable
import com.wahyusembiring.habit.presentation.composable.alertdialog.AlertDialog
import com.wahyusembiring.habit.presentation.composable.alertdialog.ConfirmAlertDialog
import com.wahyusembiring.habit.presentation.composable.alertdialog.InformAlertDialog
import com.wahyusembiring.habit.presentation.composable.alertdialog.LoadingAlertDialog

@Composable
fun ScreenAlertDialogHandler(
   dialog: AlertDialog?,
   onDismissRequest: (dialog: AlertDialog) -> Unit
) {
   when (dialog) {
      is AlertDialog.Inform -> {
         InformAlertDialog(
            dialog = dialog,
            onDismissRequest = { onDismissRequest(dialog) }
         )
      }

      is AlertDialog.Loading -> {
         LoadingAlertDialog(dialog = dialog)
      }

      is AlertDialog.Error -> TODO()
      is AlertDialog.Confirm -> {
         ConfirmAlertDialog(
            dialog = dialog,
            onDismissRequest = { onDismissRequest(dialog) }
         )
      }

      null -> Unit
   }
}