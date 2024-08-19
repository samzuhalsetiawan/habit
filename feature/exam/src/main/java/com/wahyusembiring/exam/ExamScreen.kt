package com.wahyusembiring.exam

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.wahyusembiring.ui.component.button.AddAttachmentButton
import com.wahyusembiring.ui.component.button.AddDateButton
import com.wahyusembiring.ui.component.button.AddReminderButton
import com.wahyusembiring.ui.component.button.AddSubjectButton
import com.wahyusembiring.ui.component.button.ExamCategoryPickerButton
import com.wahyusembiring.ui.component.modalbottomsheet.component.CloseAndSaveHeader
import com.wahyusembiring.ui.component.popup.PopUpHandler
import com.wahyusembiring.ui.theme.spacing

@Composable
fun ExamScreen(
   viewModel: ExamScreenViewModel,
   navigateBack: () -> Unit
) {
   val state by viewModel.state.collectAsState()
   ExamScreen(
      state = state,
      onUIEvent = viewModel::onUIEvent,
      navigateBack = navigateBack
   )
}

@Composable
private fun ExamScreen(
   state: ExamScreenUIState,
   onUIEvent: (ExamScreenUIEvent) -> Unit,
   navigateBack: () -> Unit
) {
   Column(
      modifier = Modifier.fillMaxSize()
   ) {
      CloseAndSaveHeader(
         onCloseButtonClicked = { navigateBack() },
         onSaveButtonClicked = { onUIEvent(ExamScreenUIEvent.OnSaveExamButtonClick) },
         closeButtonDescription = stringResource(R.string.close_add_exam_sheet)
      )
      Column(
         modifier = Modifier.padding(MaterialTheme.spacing.Medium)
      ) {
         OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            label = {
               Text(text = stringResource(R.string.exam_name))
            },
            leadingIcon = {
               Icon(
                  painter = painterResource(id = com.wahyusembiring.ui.R.drawable.ic_title),
                  contentDescription = stringResource(R.string.exam_name),
                  tint = MaterialTheme.colorScheme.primary
               )
            },
            singleLine = true,
            value = state.name,
            onValueChange = { onUIEvent(ExamScreenUIEvent.OnExamNameChanged(it)) },
         )
         AddDateButton(
            date = state.date,
            onClicked = { onUIEvent(ExamScreenUIEvent.OnExamDatePickerClick) }
         )
         AddReminderButton(
            time = state.time,
            onClicked = { onUIEvent(ExamScreenUIEvent.OnExamTimePickerClick) }
         )
         ExamCategoryPickerButton(
            examCategory = state.category,
            onClicked = { onUIEvent(ExamScreenUIEvent.OnExamCategoryPickerClick) }
         )
         AddSubjectButton(
            subject = state.subject,
            onClicked = { onUIEvent(ExamScreenUIEvent.OnExamSubjectPickerClick) }
         )
         AddAttachmentButton(
            attachments = state.attachments,
            onClicked = { onUIEvent(ExamScreenUIEvent.OnExamAttachmentPickerClick) }
         )
      }
   }

   PopUpHandler(
      popUps = state.popUps,
      initialCategoryForExamCategoryPicker = state.category,
   )
}