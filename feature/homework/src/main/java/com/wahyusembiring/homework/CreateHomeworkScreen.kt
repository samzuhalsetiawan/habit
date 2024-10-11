package com.wahyusembiring.homework

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.wahyusembiring.common.navigation.Screen
import com.wahyusembiring.common.util.getNotificationReminderPermission
import com.wahyusembiring.ui.component.button.AddAttachmentButton
import com.wahyusembiring.ui.component.button.AddDateButton
import com.wahyusembiring.ui.component.button.AddReminderButton
import com.wahyusembiring.ui.component.button.AddSubjectButton
import com.wahyusembiring.ui.component.modalbottomsheet.component.NavigationAndActionButtonHeader
import com.wahyusembiring.ui.component.popup.PopUpHandler
import com.wahyusembiring.ui.theme.HabitTheme
import com.wahyusembiring.ui.theme.spacing
import com.wahyusembiring.ui.util.checkForPermissionOrLaunchPermissionLauncher

@Composable
fun CreateHomeworkScreen(
    viewModel: CreateHomeworkScreenViewModel,
    navController: NavHostController,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    CreateHomeworkScreen(
        modifier = Modifier,
        state = state,
        onUIEvent = {
            when (it) {
                is CreateHomeworkUIEvent.OnNavigateBack -> navController.popBackStack()
                is CreateHomeworkUIEvent.OnNavigateToCreateSubjectScreen -> navController.navigate(
                    Screen.CreateSubject
                )

                else -> viewModel.onUIEvent(it)
            }
        },
    )
}

@Composable
private fun CreateHomeworkScreen(
    modifier: Modifier = Modifier,
    state: CreateHomeworkScreenUIState,
    onUIEvent: (CreateHomeworkUIEvent) -> Unit,
) {
    val context = LocalContext.current

    val notificationPermissionRequestLauncher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.RequestMultiplePermissions()
        ) {
            if (it.values.all { permission -> permission }) {
                onUIEvent(CreateHomeworkUIEvent.OnPickTimeButtonClicked)
            }
        }

    Scaffold { paddingValues ->
        Column(
            modifier = modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            NavigationAndActionButtonHeader(
                onNavigationButtonClicked = { onUIEvent(CreateHomeworkUIEvent.OnNavigateBack) },
                onActionButtonClicked = {
                    onUIEvent(
                        CreateHomeworkUIEvent.OnSaveHomeworkButtonClicked(
                            context
                        )
                    )
                },
                actionButtonText = if (state.isEditMode) stringResource(R.string.edit) else stringResource(
                    R.string.save
                ),
                navigationButtonDescription = stringResource(R.string.close_add_homework_sheet)
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
                    onClicked = {
                        checkForPermissionOrLaunchPermissionLauncher(
                            context = context,
                            permissionToRequest = getNotificationReminderPermission(),
                            permissionRequestLauncher = notificationPermissionRequestLauncher,
                            onPermissionAlreadyGranted = {
                                onUIEvent(CreateHomeworkUIEvent.OnPickTimeButtonClicked)
                            },
                        )
                    }
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
                    navigateToCreateSubjectScreen = {
                        onUIEvent(CreateHomeworkUIEvent.OnNavigateToCreateSubjectScreen)
                    },
                    subjects = state.subjects
                )

            }
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun CreateHomeworkScreenPreview() {
    HabitTheme {
        CreateHomeworkScreen(
            state = CreateHomeworkScreenUIState(),
            onUIEvent = {}
        )
    }
}