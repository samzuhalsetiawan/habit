package com.wahyusembiring.reminder

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import com.wahyusembiring.ui.component.button.AddAttachmentButton
import com.wahyusembiring.ui.component.button.AddDateButton
import com.wahyusembiring.ui.component.button.AddReminderButton
import com.wahyusembiring.ui.component.button.ChooseColorButton
import com.wahyusembiring.ui.component.modalbottomsheet.component.NavigationAndActionButtonHeader
import com.wahyusembiring.ui.component.popup.PopUpHandler
import com.wahyusembiring.ui.theme.spacing

@Composable
fun CreateReminderScreen(
    viewModel: CreateReminderScreenViewModel,
    navController: NavHostController,
) {
    val state by viewModel.state.collectAsState()

    CreateReminderScreen(
        state = state,
        onUIEvent = {
            when (it) {
                is CreateReminderScreenUIEvent.OnNavigateBack -> navController.popBackStack()
                else -> viewModel.onUIEvent(it)
            }
        }
    )
}

@Composable
private fun CreateReminderScreen(
    state: CreateReminderScreenUIState,
    onUIEvent: (CreateReminderScreenUIEvent) -> Unit,
) {
    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            NavigationAndActionButtonHeader(
                onNavigationButtonClicked = { onUIEvent(CreateReminderScreenUIEvent.OnNavigateBack) },
                actionButtonText = if (state.isEditMode) {
                    stringResource(R.string.edit)
                } else {
                    stringResource(R.string.save)
                },
                onActionButtonClicked = { onUIEvent(CreateReminderScreenUIEvent.OnSaveButtonClicked) },
                navigationButtonDescription = stringResource(R.string.close_create_reminder_sheet)
            )
            Column(
                modifier = Modifier.padding(MaterialTheme.spacing.Medium)
            ) {
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    label = {
                        Text(text = stringResource(R.string.reminder_title))
                    },
                    leadingIcon = {
                        Icon(
                            painter = painterResource(id = com.wahyusembiring.ui.R.drawable.ic_title),
                            contentDescription = stringResource(R.string.reminder_title),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    },
                    singleLine = true,
                    value = state.title,
                    onValueChange = { onUIEvent(CreateReminderScreenUIEvent.OnTitleChanged(it)) },
                )
                AddDateButton(
                    date = state.date,
                    onClicked = { onUIEvent(CreateReminderScreenUIEvent.OnDatePickerButtonClick) }
                )
                AddReminderButton(
                    time = state.time,
                    onClicked = { onUIEvent(CreateReminderScreenUIEvent.OnTimePickerButtonClick) }
                )
                ChooseColorButton(
                    color = state.color,
                    onClick = { onUIEvent(CreateReminderScreenUIEvent.OnColorPickerButtonClick) }
                )
                AddAttachmentButton(
                    attachments = state.attachments,
                    onClicked = { onUIEvent(CreateReminderScreenUIEvent.OnAttachmentPickerButtonClick) }
                )
            }
        }

        PopUpHandler(
            popUps = state.popUps,
            initialColorForColorPicker = state.color
        )
    }
}