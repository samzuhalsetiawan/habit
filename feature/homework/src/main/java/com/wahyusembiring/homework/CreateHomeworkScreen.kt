package com.wahyusembiring.homework

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.wahyusembiring.data.model.Subject
import com.wahyusembiring.ui.component.popup.alertdialog.confirmation.ConfirmationAlertDialog
import com.wahyusembiring.ui.component.popup.alertdialog.error.ErrorAlertDialog
import com.wahyusembiring.ui.component.popup.alertdialog.information.InformationAlertDialog
import com.wahyusembiring.ui.component.popup.alertdialog.loading.LoadingAlertDialog
import com.wahyusembiring.ui.component.popup.picker.datepicker.DatePicker
import com.wahyusembiring.ui.component.modalbottomsheet.component.AddAttachmentButton
import com.wahyusembiring.ui.component.modalbottomsheet.component.AddDateButton
import com.wahyusembiring.ui.component.modalbottomsheet.component.AddReminderButton
import com.wahyusembiring.ui.component.modalbottomsheet.component.AddSubjectButton
import com.wahyusembiring.ui.component.modalbottomsheet.component.CloseAndSaveHeader
import com.wahyusembiring.ui.component.popup.AlertDialog
import com.wahyusembiring.ui.component.popup.Picker
import com.wahyusembiring.ui.component.popup.PopUp
import com.wahyusembiring.ui.component.popup.PopUpHandler
import com.wahyusembiring.ui.component.popup.picker.attachmentpicker.AttachmentPicker
import com.wahyusembiring.ui.component.popup.picker.subjectpicker.SubjectPicker
import com.wahyusembiring.ui.component.popup.picker.timepicker.TimePicker
import com.wahyusembiring.ui.theme.HabitTheme
import com.wahyusembiring.ui.theme.spacing
import kotlinx.coroutines.launch

@Composable
fun CreateHomeworkScreen(
   navigateBack: () -> Unit,
   navigateToCreateSubjectScreen: () -> Unit,
   viewModel: CreateHomeworkScreenViewModel,
) {
   val state by viewModel.state.collectAsStateWithLifecycle()
   val context = LocalContext.current

   CreateHomeworkScreen(
      modifier = Modifier,
      state = state,
      onUIEvent = viewModel::onUIEvent,
      navigateBack = navigateBack,
      navigateToCreateSubjectScreen = navigateToCreateSubjectScreen
   )
}

@Composable
private fun CreateHomeworkScreen(
   modifier: Modifier = Modifier,
   state: CreateHomeworkScreenUIState,
   onUIEvent: (CreateHomeworkUIEvent) -> Unit,
   navigateBack: () -> Unit,
   navigateToCreateSubjectScreen: () -> Unit,
) {

   Column(
      modifier = modifier.fillMaxSize()
   ) {
      CloseAndSaveHeader(
         onCloseButtonClicked = { navigateBack() },
         onSaveButtonClicked = { onUIEvent(CreateHomeworkUIEvent.OnSaveHomeworkButtonClicked) },
         closeButtonDescription = stringResource(R.string.close_add_homework_sheet)
      )
      Column(
         modifier = Modifier.padding(MaterialTheme.spacing.Medium)
      ) {
         OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            label = {
               Text(text = stringResource(R.string.homework_title))
            },
            leadingIcon = {
               Icon(
                  painter = painterResource(id = com.wahyusembiring.ui.R.drawable.ic_title),
                  contentDescription = stringResource(R.string.homework_title),
                  tint = MaterialTheme.colorScheme.primary
               )
            },
            singleLine = true,
            value = state.homeworkTitle,
            onValueChange = { onUIEvent(CreateHomeworkUIEvent.OnHomeworkTitleChanged(it)) },
         )
         AddDateButton(
            date = state.date,
            onClicked = { onUIEvent(CreateHomeworkUIEvent.OnPickDateButtonClicked) }
         )
         AddReminderButton(
            time = state.time,
            onClicked = { onUIEvent(CreateHomeworkUIEvent.OnPickTimeButtonClicked) }
         )
         AddSubjectButton(
            subject = state.subject,
            onClicked = { onUIEvent(CreateHomeworkUIEvent.OnPickSubjectButtonClicked) }
         )
         AddAttachmentButton(
            attachments = state.attachments,
            onClicked = { onUIEvent(CreateHomeworkUIEvent.OnPickAttachmentButtonClicked) }
         )

         PopUpHandler(
            popUps = state.popUps,
            navigateToCreateSubjectScreen = { navigateToCreateSubjectScreen() },
            subjects = state.subjects
         )

      }
   }
}


@Preview(showBackground = true)
@Composable
private fun CreateHomeworkScreenPreview() {
   HabitTheme {
      CreateHomeworkScreen(
         state = CreateHomeworkScreenUIState(),
         onUIEvent = {},
         navigateBack = {},
         navigateToCreateSubjectScreen = {}
      )
   }
}