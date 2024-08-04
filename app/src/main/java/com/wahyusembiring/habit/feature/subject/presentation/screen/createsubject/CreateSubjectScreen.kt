package com.wahyusembiring.habit.feature.subject.presentation.screen.createsubject

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
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
import com.wahyusembiring.habit.core.presentation.composable.modalbottomsheet.component.TitleInputTextField
import com.wahyusembiring.habit.core.presentation.composable.picker.colorpicker.ColorPickerModalBottomSheet
import com.wahyusembiring.habit.core.presentation.composable.textinput.TextInputWithLeadingIcon
import com.wahyusembiring.habit.theme.spacing


fun NavGraphBuilder.createSubjectScreen(
   navController: NavHostController
) {
   composable<Screen.CreateSubject> {
      val viewModel: CreateSubjectViewModel = viewModel(
         factory = viewModelFactory {
            addInitializer(CreateSubjectViewModel::class) {
               CreateSubjectViewModel(
                  subjectRepository = App.appModule.subjectRepository
               )
            }
            build()
         }
      )
      CreateSubjectScreen(
         navController = navController,
         viewModel = viewModel
      )
   }
}

@Composable
fun CreateSubjectScreen(
   navController: NavHostController,
   viewModel: CreateSubjectViewModel,
) {
   val state by viewModel.state.collectAsStateWithLifecycle()
   val context = LocalContext.current

   CreateSubjectScreen(
      modifier = Modifier.fillMaxSize(),
      state = state,
      onUIEvent = { viewModel.onUIEvent(it) },
      onNavigateBack = { navController.popBackStack() }
   )
}

@Composable
private fun CreateSubjectScreen(
   modifier: Modifier = Modifier,
   state: CreateSubjectScreenUIState,
   onUIEvent: (CreateSubjectScreenUIEvent) -> Unit,
   onNavigateBack: () -> Unit,
) {

   Column(
      modifier = modifier
   ) {
      BackAndSaveHeader(
         onBackButtonClicked = { onNavigateBack() },
         onSaveButtonClicked = { onUIEvent(CreateSubjectScreenUIEvent.OnSaveButtonClicked) }
      )
      TitleInputTextField(
         label = stringResource(R.string.subject_name),
         title = state.name,
         onTitleChange = { onUIEvent(CreateSubjectScreenUIEvent.OnSubjectNameChanged(it)) }
      )
      TextInputWithLeadingIcon(
         modifier = Modifier.fillMaxWidth(),
         icon = painterResource(id = R.drawable.ic_location),
         iconDescription = stringResource(R.string.room),
         value = state.room,
         onValueChange = { onUIEvent(CreateSubjectScreenUIEvent.OnRoomChanged(it)) },
      )
      ChooseColorButton(
         color = state.color,
         onClick = { onUIEvent(CreateSubjectScreenUIEvent.OnPickColorButtonClicked) }
      )
   }

   PopUpHandler(
      popUp = state.popUp,
      onUIEvent = onUIEvent
   )
}

@Composable
private fun PopUpHandler(
   popUp: CreateSubjectScreenPopUp?,
   onUIEvent: (CreateSubjectScreenUIEvent) -> Unit,
) {
   when (popUp) {
      is CreateSubjectScreenPopUp.ColorPicker -> {
         ColorPickerModalBottomSheet(
            onColorConfirmed = { onUIEvent(CreateSubjectScreenUIEvent.OnColorConfirmed(it)) },
            onDismissRequest = {
               onUIEvent(
                  CreateSubjectScreenUIEvent.HidePopUp(
                     CreateSubjectScreenPopUp.ColorPicker
                  )
               )
            },
         )
      }

      is CreateSubjectScreenPopUp.NameIsRequired -> {
         ErrorAlertDialog(
            message = stringResource(R.string.subject_name_is_required),
            buttonText = stringResource(id = R.string.ok),
            onButtonClicked = {
               onUIEvent(
                  CreateSubjectScreenUIEvent.HidePopUp(
                     CreateSubjectScreenPopUp.NameIsRequired
                  )
               )
            },
            onDismissRequest = {
               onUIEvent(
                  CreateSubjectScreenUIEvent.HidePopUp(
                     CreateSubjectScreenPopUp.NameIsRequired
                  )
               )
            }
         )
      }

      is CreateSubjectScreenPopUp.RoomIsRequired -> {
         ErrorAlertDialog(
            message = stringResource(R.string.room_is_required),
            buttonText = stringResource(id = R.string.ok),
            onButtonClicked = {
               onUIEvent(
                  CreateSubjectScreenUIEvent.HidePopUp(
                     CreateSubjectScreenPopUp.RoomIsRequired
                  )
               )
            },
            onDismissRequest = {
               onUIEvent(
                  CreateSubjectScreenUIEvent.HidePopUp(
                     CreateSubjectScreenPopUp.RoomIsRequired
                  )
               )
            }
         )
      }

      is CreateSubjectScreenPopUp.SaveSubjectSuccess -> {
         InformationAlertDialog(
            title = stringResource(id = R.string.success),
            message = stringResource(R.string.subject_saved),
            buttonText = stringResource(id = R.string.ok),
            onButtonClicked = {
               onUIEvent(
                  CreateSubjectScreenUIEvent.HidePopUp(
                     CreateSubjectScreenPopUp.SaveSubjectSuccess
                  )
               )
            },
            onDismissRequest = {
               onUIEvent(
                  CreateSubjectScreenUIEvent.HidePopUp(
                     CreateSubjectScreenPopUp.SaveSubjectSuccess
                  )
               )
            }
         )
      }

      is CreateSubjectScreenPopUp.SaveSubjectLoading -> {
         LoadingAlertDialog(stringResource(id = R.string.saving))
      }

      is CreateSubjectScreenPopUp.SaveSubjectConfirmation -> {
         ConfirmationAlertDialog(
            onPositiveButtonClick = { /*TODO*/ },
            onNegativeButtonClick = { /*TODO*/ },
            title = "Save subject",
            message = "Are you sure you want to save this subject?",
            positiveButtonText = stringResource(id = R.string.save),
            negativeButtonText = stringResource(id = R.string.cancel),
         ) {

         }
      }

      null -> Unit
   }
}

@Composable
private fun ChooseColorButton(
   color: Color,
   onClick: () -> Unit
) {
   ListItem(
      modifier = Modifier
         .clickable(onClick = onClick),
      leadingContent = {
         Box(
            modifier = Modifier
               .size(MaterialTheme.spacing.Medium)
               .background(
                  color = color,
                  shape = RoundedCornerShape(50)
               )
               .clip(RoundedCornerShape(50))
         )
      },
      headlineContent = {
         Text(
            text = stringResource(R.string.color),
            color = color
         )
      }
   )
}

@Composable
private fun BackAndSaveHeader(
   onBackButtonClicked: () -> Unit,
   onSaveButtonClicked: () -> Unit
) {
   Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically
   ) {
      IconButton(
         onClick = onBackButtonClicked
      ) {
         Icon(
            painter = painterResource(R.drawable.ic_back_arrow),
            contentDescription = stringResource(R.string.back)
         )
      }
      Button(
         modifier = Modifier.padding(end = MaterialTheme.spacing.Medium),
         onClick = onSaveButtonClicked
      ) {
         Text(text = stringResource(id = R.string.save))
      }
   }
}