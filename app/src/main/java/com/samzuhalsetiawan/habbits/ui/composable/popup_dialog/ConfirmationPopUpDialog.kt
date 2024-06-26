package com.samzuhalsetiawan.habbits.ui.composable.popup_dialog

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.samzuhalsetiawan.habbits.ui.localcomposition.provider.PopUpDialog
import com.samzuhalsetiawan.habbits.ui.localcomposition.provider.PopUpDialogResponse

@Composable
fun ConfirmationPopUp(
    confirmationDialog: PopUpDialog.Confirmation,
    onDismissRequest: () -> Unit
) {
    ConfirmationPopUpDialog(
        title = confirmationDialog.title,
        message = confirmationDialog.message,
        positiveButton = {
            TextButton(
                onClick = {
                    confirmationDialog.callback.onResponse(PopUpDialogResponse.POSITIVE)
                    onDismissRequest()
                }
            ) {
                Text(text = confirmationDialog.acknowledgeButtonText)
            }
        },
        onDismissRequest = {
            confirmationDialog.callback.onResponse(PopUpDialogResponse.DISMISS)
            onDismissRequest()
        }
    )
}

@Composable
private fun ConfirmationPopUpDialog(
    modifier: Modifier = Modifier,
    title: String,
    message: String,
    positiveButton: @Composable () -> Unit,
    onDismissRequest: () -> Unit,
) {
    AlertDialog(
        modifier = modifier,
        title = {
            Text(text = title)
        },
        icon = {
            Icon(
                imageVector = Icons.Default.Warning,
                contentDescription = "Pop Up Icon"
            )
        },
        text = {
            Text(text = message)
        },
        confirmButton = positiveButton,
        onDismissRequest = onDismissRequest,
    )
}