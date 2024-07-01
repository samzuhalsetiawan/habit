package com.samzuhalsetiawan.habbits.ui.handler

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.samzuhalsetiawan.habbits.ui.composable.popup_dialog.BinaryChoicePopUpDialog
import com.samzuhalsetiawan.habbits.ui.composable.popup_dialog.ConfirmationPopUp
import com.samzuhalsetiawan.habbits.ui.composable.popup_dialog.DailyReminderTimePickerPopUpDialog
import com.samzuhalsetiawan.habbits.ui.composable.popup_dialog.ErrorPopUpDialog
import com.samzuhalsetiawan.habbits.ui.composable.popup_dialog.LoadingPopUpDialog
import com.samzuhalsetiawan.habbits.ui.composable.popup_dialog.TimePickerPopUpDialog
import com.samzuhalsetiawan.habbits.ui.localcomposition.provider.LocalPopUpController
import com.samzuhalsetiawan.habbits.ui.localcomposition.provider.PopUpDialog
import com.samzuhalsetiawan.habbits.ui.localcomposition.provider.PopUpManager

@Composable
fun PopUpDialogHandler(
   popUpDialog: PopUpDialog?
) {
   val popUpController = LocalPopUpController.current

   LaunchedEffect(popUpDialog) {
      if (popUpDialog == null) {
         popUpController.clear()
      } else {
         popUpController.show(popUpDialog)
      }
   }
}

@Composable
fun PopUpDialogStateHandler(
   popUpDialogState: PopUpDialog?,
   popUpManager: PopUpManager
) {

   when (popUpDialogState) {
      is PopUpDialog.Confirmation -> {
         ConfirmationPopUp(
            confirmationDialog = popUpDialogState,
            onDismissRequest = popUpManager::closePopUpDialog
         )
      }

      is PopUpDialog.BinaryChoice -> {
         BinaryChoicePopUpDialog(
            dialog = popUpDialogState,
            onDismissRequest = popUpManager::closePopUpDialog
         )
      }

      is PopUpDialog.Error -> {
         ErrorPopUpDialog(
            errorDialog = popUpDialogState,
            onDismissRequest = popUpManager::closePopUpDialog
         )
      }

      is PopUpDialog.BasicTimePicker -> {
         TimePickerPopUpDialog(
            dialog = popUpDialogState,
            onDismissRequest = popUpManager::closePopUpDialog
         )
      }

      is PopUpDialog.DailyReminderTimePicker -> {
         DailyReminderTimePickerPopUpDialog(
            dialog = popUpDialogState,
            onDismissRequest = popUpManager::closePopUpDialog
         )
      }

      PopUpDialog.Loading -> {
         LoadingPopUpDialog()
      }

      null -> return
   }
}