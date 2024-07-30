package com.wahyusembiring.habit.feature.homework.presentation.screen.createhomework

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.wahyusembiring.habit.App
import com.wahyusembiring.habit.R
import com.wahyusembiring.habit.core.presentation.Screen
import com.wahyusembiring.habit.core.presentation.composable.alertdialog.confirmation.ConfirmationAlertDialog
import com.wahyusembiring.habit.core.presentation.composable.alertdialog.error.ErrorAlertDialog
import com.wahyusembiring.habit.core.presentation.composable.alertdialog.information.InformationAlertDialog
import com.wahyusembiring.habit.core.presentation.composable.alertdialog.loading.LoadingAlertDialog
import com.wahyusembiring.habit.core.presentation.composable.picker.datepicker.DatePicker
import com.wahyusembiring.habit.core.presentation.composable.modalbottomsheet.component.AddAttachmentButton
import com.wahyusembiring.habit.core.presentation.composable.modalbottomsheet.component.AddDateButton
import com.wahyusembiring.habit.core.presentation.composable.modalbottomsheet.component.AddReminderButton
import com.wahyusembiring.habit.core.presentation.composable.modalbottomsheet.component.AddSubjectButton
import com.wahyusembiring.habit.core.presentation.composable.modalbottomsheet.component.CloseAndSaveHeader
import com.wahyusembiring.habit.core.presentation.composable.modalbottomsheet.component.TitleInputTextField
import com.wahyusembiring.habit.core.presentation.composable.modalbottomsheet.pickattachment.PickAttachmentModalBottomSheet
import com.wahyusembiring.habit.core.presentation.composable.modalbottomsheet.picksubject.PickSubjectModalBottomSheet
import com.wahyusembiring.habit.core.presentation.composable.picker.timepicker.TimePicker
import com.wahyusembiring.habit.feature.subject.domain.model.Subject
import kotlinx.coroutines.launch

fun NavGraphBuilder.createHomeworkScreen(
   navController: NavHostController
) {
   composable<Screen.CreateHomework> {
      val viewModel: CreateHomeworkScreenViewModel = viewModel(
         viewModelStoreOwner = it,
         factory = viewModelFactory {
            addInitializer(CreateHomeworkScreenViewModel::class) {
               CreateHomeworkScreenViewModel(
                  homeworkRepository = App.appModule.homeworkRepository,
                  subjectRepository = App.appModule.subjectRepository
               )
            }
            build()
         }
      )
      CreateHomeworkScreen(
         navController = navController,
         viewModel = viewModel
      )
   }
}

@Composable
fun CreateHomeworkScreen(
   navController: NavHostController,
   viewModel: CreateHomeworkScreenViewModel,
) {
   val state by viewModel.state.collectAsStateWithLifecycle()
   val context = LocalContext.current

   CreateHomeworkScreen(
      state = state,
      onUIEvent = viewModel::onUIEvent,
      navigateBack = { navController.popBackStack() },
      navigateToCreateSubjectScreen = { navController.navigate(Screen.CreateSubject) }
   )
}

@Composable
private fun CreateHomeworkScreen(
   state: CreateHomeworkScreenUIState,
   onUIEvent: (CreateHomeworkUIEvent) -> Unit,
   navigateBack: () -> Unit,
   navigateToCreateSubjectScreen: () -> Unit,
) {

   Column {
      CloseAndSaveHeader(
         onCloseButtonClicked = { navigateBack() },
         onSaveButtonClicked = { onUIEvent(CreateHomeworkUIEvent.OnSaveHomeworkButtonClicked) },
         closeButtonDescription = stringResource(R.string.close_add_homework_sheet)
      )
      TitleInputTextField(
         label = stringResource(R.string.homework_title),
         title = state.homeworkTitle,
         onTitleChange = { onUIEvent(CreateHomeworkUIEvent.OnHomeworkTitleChanged(it)) }
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
         popUp = state.popUp,
         onUIEvent = onUIEvent,
         navigateToCreateSubjectScreen = { navigateToCreateSubjectScreen() },
         subjects = state.subjects
      )

   }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PopUpHandler(
   popUp: CreateHomeworkScreenPopUp?,
   onUIEvent: (CreateHomeworkUIEvent) -> Unit,
   navigateToCreateSubjectScreen: () -> Unit,
   subjects: List<Subject>,
) {
   val sheetState = rememberModalBottomSheetState()
   val coroutineScope = rememberCoroutineScope()

   when (popUp) {
      CreateHomeworkScreenPopUp.DatePicker -> {
         DatePicker(
            onDateSelected = { onUIEvent(CreateHomeworkUIEvent.OnDateSelected(it)) },
            onDismissRequest = { onUIEvent(CreateHomeworkUIEvent.HidePopUp(CreateHomeworkScreenPopUp.DatePicker)) },
         )
      }

      CreateHomeworkScreenPopUp.TimePicker -> {
         TimePicker(
            onTimeSelected = { onUIEvent(CreateHomeworkUIEvent.OnTimeSelected(it)) },
            onDismissRequest = { onUIEvent(CreateHomeworkUIEvent.HidePopUp(CreateHomeworkScreenPopUp.TimePicker)) }
         )
      }

      CreateHomeworkScreenPopUp.SubjectPicker -> {
         PickSubjectModalBottomSheet(
            onSubjectSelected = { onUIEvent(CreateHomeworkUIEvent.OnSubjectSelected(it)) },
            navigateToCreateSubjectScreen = { navigateToCreateSubjectScreen() },
            onDismissRequest = {
               coroutineScope.launch { sheetState.hide() }.invokeOnCompletion {
                  onUIEvent(CreateHomeworkUIEvent.HidePopUp(CreateHomeworkScreenPopUp.SubjectPicker))
               }
            },
            sheetState = sheetState,
            subjects = subjects,
         )
      }

      CreateHomeworkScreenPopUp.AttachmentPicker -> {
         PickAttachmentModalBottomSheet(
            onAttachmentsConfirmed = { onUIEvent(CreateHomeworkUIEvent.OnAttachmentsConfirmed(it)) },
            onDismissRequest = { onUIEvent(CreateHomeworkUIEvent.HidePopUp(CreateHomeworkScreenPopUp.AttachmentPicker)) }
         )
      }

      CreateHomeworkScreenPopUp.SaveHomeworkConfirmation -> {
         ConfirmationAlertDialog(
            onPositiveButtonClick = { onUIEvent(CreateHomeworkUIEvent.OnSaveHomeworkButtonClicked) },
            onNegativeButtonClick = {
               onUIEvent(
                  CreateHomeworkUIEvent.HidePopUp(
                     CreateHomeworkScreenPopUp.SaveHomeworkConfirmation
                  )
               )
            },
            title = stringResource(id = R.string.save_homework),
            message = stringResource(id = R.string.are_you_sure_you_want_to_save_this_homework),
            positiveButtonText = stringResource(id = R.string.save),
            negativeButtonText = stringResource(id = R.string.cancel),
            onDismissRequest = { onUIEvent(CreateHomeworkUIEvent.HidePopUp(CreateHomeworkScreenPopUp.SaveHomeworkConfirmation)) }
         )
      }

      CreateHomeworkScreenPopUp.HomeworkTitleIsRequired -> {
         ErrorAlertDialog(
            message = stringResource(id = R.string.homework_title_is_required),
            buttonText = stringResource(id = R.string.ok),
            onButtonClicked = { onUIEvent(CreateHomeworkUIEvent.HidePopUp(CreateHomeworkScreenPopUp.HomeworkTitleIsRequired)) },
            onDismissRequest = { onUIEvent(CreateHomeworkUIEvent.HidePopUp(CreateHomeworkScreenPopUp.HomeworkTitleIsRequired)) }
         )
      }

      CreateHomeworkScreenPopUp.DateIsRequired -> {
         ErrorAlertDialog(
            message = stringResource(R.string.due_date_is_required),
            buttonText = stringResource(id = R.string.ok),
            onButtonClicked = { onUIEvent(CreateHomeworkUIEvent.HidePopUp(CreateHomeworkScreenPopUp.DateIsRequired)) },
            onDismissRequest = { onUIEvent(CreateHomeworkUIEvent.HidePopUp(CreateHomeworkScreenPopUp.DateIsRequired)) }
         )
      }

      CreateHomeworkScreenPopUp.SubjectIsRequired -> {
         ErrorAlertDialog(
            message = stringResource(R.string.subject_is_required),
            buttonText = stringResource(id = R.string.ok),
            onButtonClicked = { onUIEvent(CreateHomeworkUIEvent.HidePopUp(CreateHomeworkScreenPopUp.SubjectIsRequired)) },
            onDismissRequest = { onUIEvent(CreateHomeworkUIEvent.HidePopUp(CreateHomeworkScreenPopUp.SubjectIsRequired)) }
         )
      }

      CreateHomeworkScreenPopUp.SaveHomeworkSuccess -> {
         InformationAlertDialog(
            onButtonClicked = { onUIEvent(CreateHomeworkUIEvent.HidePopUp(CreateHomeworkScreenPopUp.SaveHomeworkSuccess)) },
            buttonText = stringResource(id = R.string.ok),
            title = stringResource(id = R.string.success),
            message = stringResource(id = R.string.homework_saved),
            onDismissRequest = { onUIEvent(CreateHomeworkUIEvent.HidePopUp(CreateHomeworkScreenPopUp.SaveHomeworkSuccess)) }
         )
      }

      CreateHomeworkScreenPopUp.SaveHomeworkLoading -> {
         LoadingAlertDialog(stringResource(id = R.string.saving))
      }

      null -> Unit
   }
}