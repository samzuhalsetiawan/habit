package com.wahyusembiring.ui.component.popup

import com.wahyusembiring.ui.util.UIText
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Deferred

sealed class AlertDialog : PopUp {

   private val _deferred = CompletableDeferred<Result>()
   val result: Deferred<Result> = _deferred

   fun onDismiss() {
      _deferred.complete(Result.Dismiss)
   }

   fun onPositiveButtonClick() {
      _deferred.complete(Result.Positive)
   }

   fun onNegativeButtonClick() {
      _deferred.complete(Result.Negative)
   }

   data class Confirmation(
      val title: UIText,
      val message: UIText,
      val positiveButtonText: UIText = UIText.StringResource(android.R.string.ok),
      val negativeButtonText: UIText = UIText.StringResource(android.R.string.cancel),
   ) : AlertDialog()

   data class Error(
      val message: UIText,
      val buttonText: UIText = UIText.StringResource(android.R.string.ok),
   ) : AlertDialog()

   data class Information(
      val title: UIText = UIText.StringResource(android.R.string.dialog_alert_title),
      val message: UIText,
      val positiveButtonText: UIText = UIText.StringResource(android.R.string.ok),
   ) : AlertDialog()

   data class Loading(
      val message: UIText
   ) : AlertDialog()

   enum class Result {
      Positive, Negative, Dismiss
   }

}