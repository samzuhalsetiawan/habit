package com.samzuhalsetiawan.habbits.ui.localcomposition.provider

import androidx.compose.runtime.staticCompositionLocalOf

interface PopUpManager {

   fun showPopUpDialog(dialog: PopUpDialog)
   fun closePopUpDialog()

}

interface PopUpController {

   fun show(dialog: PopUpDialog)
   fun show(dialog: () -> PopUpDialog)
   fun clear()

}

class PopUpControllerImpl(
   private val popUpManager: PopUpManager
): PopUpController {

   override fun show(dialog: PopUpDialog) {
      dialog.show(popUpManager)
   }

   override fun show(dialog: () -> PopUpDialog) {
      dialog.invoke().show(popUpManager)
   }

   override fun clear() {
      popUpManager.closePopUpDialog()
   }

}

val LocalPopUpController = staticCompositionLocalOf<PopUpController> {
   error("No LocalPopUpController Provided")
}

sealed class PopUpDialog {

   fun show(popUpManager: PopUpManager) {
      popUpManager.showPopUpDialog(this)
   }
   fun close(popUpManager: PopUpManager) {
      popUpManager.closePopUpDialog()
   }

   class Confirmation(
      val title: String,
      val message: String,
      val acknowledgeButtonText: String,
      val callback: PopUpDialogCallback = PopUpDialogCallback {},
   ): PopUpDialog()

   class Error(
      val errorMessage: String,
      val acknowledgeButtonText: String = "Ok",
      val callback: PopUpDialogCallback = PopUpDialogCallback {},
   ): PopUpDialog()

   class BinaryChoice(
      val title: String,
      val message: String,
      val positiveButtonText: String,
      val negaviteButtonText: String,
      val callback: PopUpDialogCallback,
   ): PopUpDialog()

   data object Loading: PopUpDialog()

}

fun interface PopUpDialogCallback {
   fun onResponse(response: PopUpDialogResponse)
}

enum class PopUpDialogResponse {
   POSITIVE, NEGATIVE, DISMISS
}