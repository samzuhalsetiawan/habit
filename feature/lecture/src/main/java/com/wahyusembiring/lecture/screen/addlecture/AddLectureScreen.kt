package com.wahyusembiring.lecture.screen.addlecture

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.wahyusembiring.lecture.R
import com.wahyusembiring.ui.component.emailinput.EmailInput
import com.wahyusembiring.ui.component.multiaddressinput.MultiAddressInput
import com.wahyusembiring.ui.component.officehourinput.OfficeHourInput
import com.wahyusembiring.ui.component.phonenumberinput.PhoneNumberInput
import com.wahyusembiring.ui.component.popup.alertdialog.confirmation.ConfirmationAlertDialog
import com.wahyusembiring.ui.component.popup.alertdialog.error.ErrorAlertDialog
import com.wahyusembiring.ui.component.profilepicturepicker.ProfilePicturePicker
import com.wahyusembiring.ui.component.websiteinput.WebsiteInput
import com.wahyusembiring.ui.theme.spacing

@Composable
fun AddLectureScreen(
    viewModel: AddLectureScreenViewModel,
    navController: NavController
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    AddLectureScreen(
        state = state,
        onUIEvent = viewModel::onUIEvent,
        navController = navController
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AddLectureScreen(
    state: AddLectureScreenUItate,
    onUIEvent: (AddLectureScreenUIEvent) -> Unit,
    navController: NavController
) {
    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(
                        onClick = {
                            onUIEvent(AddLectureScreenUIEvent.OnBackButtonClick(navController))
                        }
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Outlined.ArrowBack,
                            contentDescription = null
                        )
                    }
                },
                title = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Button(
                            onClick = {
                                onUIEvent(AddLectureScreenUIEvent.OnSaveButtonClick)
                            }
                        ) {
                            Text(text = "Save")
                        }
                    }
                }
            )
        }
    ) { scaffoldPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(scaffoldPadding)
        ) {
            item {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    ProfilePicturePicker(
                        modifier = Modifier
                            .size(100.dp),
                        imageUri = state.profilePictureUri,
                        onImageSelected = {
                            onUIEvent(AddLectureScreenUIEvent.OnProfilePictureSelected(it))
                        }
                    )
                }
                Spacer(modifier = Modifier.height(MaterialTheme.spacing.Medium))
            }
            item {
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = MaterialTheme.spacing.Medium),
                    value = state.name,
                    onValueChange = {
                        onUIEvent(AddLectureScreenUIEvent.OnLectureNameChange(it))
                    },
                    label = {
                        Text(text = stringResource(R.string.lecture_name))
                    }
                )
                Spacer(modifier = Modifier.height(MaterialTheme.spacing.Medium))
            }
            item {
                PhoneNumberInput(
                    phoneNumbers = state.phoneNumbers,
                    onNewPhoneNumber = {
                        onUIEvent(AddLectureScreenUIEvent.OnNewPhoneNumber(it))
                    }
                )
                Spacer(modifier = Modifier.height(MaterialTheme.spacing.Medium))
            }
            item {
                EmailInput(
                    emails = state.emails,
                    onNewEmail = {
                        onUIEvent(AddLectureScreenUIEvent.OnNewEmail(it))
                    }
                )
                Spacer(modifier = Modifier.height(MaterialTheme.spacing.Medium))
            }
            item {
                MultiAddressInput(
                    addresses = state.addresses,
                    onNewAddress = {
                        onUIEvent(AddLectureScreenUIEvent.OnNewAddress(it))
                    }
                )
                Spacer(modifier = Modifier.height(MaterialTheme.spacing.Medium))
            }
            item {
                OfficeHourInput(
                    officeHours = state.officeHours,
                    onNewOfficeHour = {
                        onUIEvent(AddLectureScreenUIEvent.OnNewOfficeHour(it))
                    }
                )
                Spacer(modifier = Modifier.height(MaterialTheme.spacing.Medium))
            }
            item {
                WebsiteInput(
                    websites = state.websites,
                    onNewWebsiteAddClick = {
                        onUIEvent(AddLectureScreenUIEvent.OnNewWebsite(it))
                    }
                )
                Spacer(modifier = Modifier.height(MaterialTheme.spacing.Large))
            }
        }
    }
    if (state.showSaveConfirmationDialog) {
        ConfirmationAlertDialog(
            onPositiveButtonClick = {
                onUIEvent(AddLectureScreenUIEvent.OnSaveConfirmationDialogConfirm)
            },
            onNegativeButtonClick = {
                onUIEvent(AddLectureScreenUIEvent.OnSaveConfirmationDialogCancel)
            },
            onDismissRequest = {
                onUIEvent(AddLectureScreenUIEvent.OnSaveConfirmationDialogDismiss)
            },
            title = stringResource(R.string.save_lecture),
            message = stringResource(R.string.are_you_sure_you_want_to_save_this_lecture),
            positiveButtonText = stringResource(R.string.save),
            negativeButtonText = stringResource(R.string.cancel),
        )
    }
    if (state.errorMessage != null) {
        ErrorAlertDialog(
            message = state.errorMessage.asString(),
            buttonText = stringResource(R.string.ok),
            onDismissRequest = {
                onUIEvent(AddLectureScreenUIEvent.OnErrorDialogDismiss)
            }
        )
    }
}