package com.wahyusembiring.exam

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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import com.wahyusembiring.common.navigation.Screen
import com.wahyusembiring.common.util.getNotificationReminderPermission
import com.wahyusembiring.ui.component.button.AddAttachmentButton
import com.wahyusembiring.ui.component.button.AddDateButton
import com.wahyusembiring.ui.component.button.AddReminderButton
import com.wahyusembiring.ui.component.button.AddSubjectButton
import com.wahyusembiring.ui.component.button.ExamCategoryPickerButton
import com.wahyusembiring.ui.component.modalbottomsheet.component.NavigationAndActionButtonHeader
import com.wahyusembiring.ui.component.popup.PopUpHandler
import com.wahyusembiring.ui.theme.spacing
import com.wahyusembiring.ui.util.checkForPermissionOrLaunchPermissionLauncher

@Composable
fun ExamScreen(
    viewModel: ExamScreenViewModel,
    navController: NavHostController,
) {
    val state by viewModel.state.collectAsState()
    ExamScreen(
        state = state,
        onUIEvent = {
            when (it) {
                is ExamScreenUIEvent.OnNavigate -> navController.navigate(it.screen)
                is ExamScreenUIEvent.OnNavigateBack -> navController.popBackStack()
                else -> viewModel.onUIEvent(it)
            }
        }
    )
}

@Composable
private fun ExamScreen(
    state: ExamScreenUIState,
    onUIEvent: (ExamScreenUIEvent) -> Unit,
) {
    val context = LocalContext.current
    val notificationPermissionRequestLauncher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.RequestMultiplePermissions()
        ) {
            if (it.values.all { permission -> permission }) {
                onUIEvent(ExamScreenUIEvent.OnExamTimePickerClick)
            }
        }

    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            NavigationAndActionButtonHeader(
                onNavigationButtonClicked = { onUIEvent(ExamScreenUIEvent.OnNavigateBack) },
                actionButtonText = if (state.isEditMode) {
                    stringResource(R.string.edit)
                } else {
                    stringResource(R.string.save)
                },
                onActionButtonClicked = { onUIEvent(ExamScreenUIEvent.OnSaveExamButtonClick(context)) },
                navigationButtonDescription = stringResource(R.string.close_add_exam_sheet)
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
                    onClicked = {
                        checkForPermissionOrLaunchPermissionLauncher(
                            context = context,
                            permissionToRequest = getNotificationReminderPermission(),
                            permissionRequestLauncher = notificationPermissionRequestLauncher,
                            onPermissionAlreadyGranted = {
                                onUIEvent(ExamScreenUIEvent.OnExamTimePickerClick)
                            }
                        )
                    }
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
            subjects = state.subjects,
            navigateToCreateSubjectScreen = {
                onUIEvent(ExamScreenUIEvent.OnNavigate(Screen.CreateSubject))
            }
        )
    }
}