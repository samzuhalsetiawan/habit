package com.wahyusembiring.ui.component.popup

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.wahyusembiring.data.model.ExamCategory
import com.wahyusembiring.data.model.Subject
import com.wahyusembiring.ui.component.popup.alertdialog.confirmation.ConfirmationAlertDialog
import com.wahyusembiring.ui.component.popup.alertdialog.error.ErrorAlertDialog
import com.wahyusembiring.ui.component.popup.alertdialog.information.InformationAlertDialog
import com.wahyusembiring.ui.component.popup.alertdialog.loading.LoadingAlertDialog
import com.wahyusembiring.ui.component.popup.picker.attachmentpicker.AttachmentPicker
import com.wahyusembiring.ui.component.popup.picker.colorpicker.ColorPicker
import com.wahyusembiring.ui.component.popup.picker.datepicker.DatePicker
import com.wahyusembiring.ui.component.popup.picker.examcategorypicker.ExamCategoryPicker
import com.wahyusembiring.ui.component.popup.picker.subjectpicker.SubjectPicker
import com.wahyusembiring.ui.component.popup.picker.timepicker.TimePicker

sealed interface PopUp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PopUpHandler(
   popUps: List<PopUp>,
   initialColorForColorPicker: Color = Color.Black,
   initialCategoryForExamCategoryPicker: ExamCategory = ExamCategory.WRITTEN,
   subjects: List<Subject> = emptyList(),
   navigateToCreateSubjectScreen: () -> Unit = {},
) {

   for (popUp in popUps) {
      when (popUp) {
         is AlertDialog.Confirmation -> {
            ConfirmationAlertDialog(
               onPositiveButtonClick = popUp::onPositiveButtonClick,
               onNegativeButtonClick = popUp::onNegativeButtonClick,
               title = popUp.title.asString(),
               message = popUp.message.asString(),
               positiveButtonText = popUp.positiveButtonText.asString(),
               negativeButtonText = popUp.negativeButtonText.asString(),
               onDismissRequest = popUp::onDismiss
            )
         }

         is AlertDialog.Error -> {
            ErrorAlertDialog(
               message = popUp.message.asString(),
               buttonText = popUp.buttonText.asString(),
               onDismissRequest = popUp::onDismiss,
            )
         }

         is AlertDialog.Information -> {
            InformationAlertDialog(
               onButtonClicked = popUp::onPositiveButtonClick,
               buttonText = popUp.positiveButtonText.asString(),
               title = popUp.title.asString(),
               message = popUp.message.asString(),
               onDismissRequest = popUp::onDismiss
            )
         }

         is AlertDialog.Loading -> {
            LoadingAlertDialog(popUp.message.asString())
         }

         is Picker.AttachmentPicker -> {
            AttachmentPicker(
               onAttachmentsConfirmed = popUp::onPicked,
               onDismissRequest = popUp::dismiss
            )
         }

         is Picker.DatePicker -> {
            DatePicker(
               onDateSelected = popUp::onPicked,
               onDismissRequest = popUp::dismiss,
            )
         }

         is Picker.SubjectPicker -> {
            SubjectPicker(
               onSubjectSelected = popUp::onPicked,
               navigateToCreateSubjectScreen = { navigateToCreateSubjectScreen() },
               onDismissRequest = popUp::dismiss,
               subjects = subjects,
            )
         }

         is Picker.TimePicker -> {
            TimePicker(
               onTimeSelected = popUp::onPicked,
               onDismissRequest = popUp::dismiss
            )
         }

         is Picker.ColorPicker -> {
            ColorPicker(
               onColorConfirmed = popUp::onPicked,
               onDismissRequest = popUp::dismiss,
               initialColor = initialColorForColorPicker,
            )
         }

         is Picker.ExamCategoryPicker -> {
            ExamCategoryPicker(
               onDismissRequest = popUp::dismiss,
               onCategoryPicked = popUp::onPicked,
               initialCategory = initialCategoryForExamCategoryPicker
            )
         }
      }
   }
}