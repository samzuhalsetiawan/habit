package com.samzuhalsetiawan.habbits.ui.localcomposition.provider

import androidx.compose.runtime.staticCompositionLocalOf
import com.samzuhalsetiawan.habbits.ui.localcomposition.provider.PopUpDialog.BasicTimePicker.Result
import java.time.DayOfWeek

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
) : PopUpController {

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
      val callback: PopUpDialogCallback<Unit> = PopUpDialogCallback {},
   ) : PopUpDialog()

   class Error(
      val errorMessage: String,
      val acknowledgeButtonText: String = "Ok",
      val callback: PopUpDialogCallback<Unit> = PopUpDialogCallback {},
   ) : PopUpDialog()

   class BinaryChoice(
      val title: String,
      val message: String,
      val positiveButtonText: String,
      val negaviteButtonText: String,
      val callback: PopUpDialogCallback<Unit>,
   ) : PopUpDialog()

   interface TimePicker <T> {
      val title: String
      val positiveButtonText: String
      val negaviteButtonText: String
      val callback: PopUpDialogCallback<T>
   }

   class BasicTimePicker(
      override val title: String,
      override val positiveButtonText: String,
      override val negaviteButtonText: String,
      override val callback: PopUpDialogCallback<Result>
   ): PopUpDialog(), TimePicker<Result> {
      class Result(
         val hour: Int,
         val minute: Int,
      )
   }

   class DailyReminderTimePicker(
      override val title: String,
      override val positiveButtonText: String,
      override val negaviteButtonText: String,
      override val callback: PopUpDialogCallback<Result>
   ): PopUpDialog(), TimePicker<DailyReminderTimePicker.Result> {
      class Result(
         val hour: Int,
         val minute: Int,
         val days: List<DayOfWeek>
      )
   }

   data object Loading : PopUpDialog()

}

fun interface PopUpDialogCallback<T> {
   fun callback(result: PopUpDialogResult<T>)
}

data class PopUpDialogResult<T>(
   val response: PopUpDialogResponse,
   val data: T? = null
)

enum class PopUpDialogResponse {
   POSITIVE, NEGATIVE, DISMISS
}