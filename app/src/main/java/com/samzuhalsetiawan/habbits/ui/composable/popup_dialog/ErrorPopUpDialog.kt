package com.samzuhalsetiawan.habbits.ui.composable.popup_dialog

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.samzuhalsetiawan.habbits.ui.localcomposition.provider.PopUpDialog
import com.samzuhalsetiawan.habbits.ui.localcomposition.provider.PopUpDialogResponse

@Composable
fun ErrorPopUpDialog(
   errorDialog: PopUpDialog.Error,
   onDismissRequest: () -> Unit,
   modifier: Modifier = Modifier
) {
   AlertDialog(
      modifier = modifier,
      icon = {
         Icon(
            imageVector = Icons.Default.Warning,
            contentDescription = "Error Icon",
            tint = MaterialTheme.colorScheme.error
         )
      },
      title = {
         Text(text = "Error")
      },
      text = {
         Text(
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            text = errorDialog.errorMessage
         )
      },
      onDismissRequest = {
         errorDialog.callback.onResponse(PopUpDialogResponse.DISMISS)
         onDismissRequest()
      },
      confirmButton = {
         TextButton(onClick = {
            errorDialog.callback.onResponse(PopUpDialogResponse.POSITIVE)
            onDismissRequest()
         }) {
            Text(
               text = errorDialog.acknowledgeButtonText,
               color = MaterialTheme.colorScheme.error
            )
         }
      }
   )
}

@Preview(showBackground = true)
@Composable
private fun ErrorPopUpDialogPreview() {
   ErrorPopUpDialog(
      errorDialog = PopUpDialog.Error(
         errorMessage = "Error Message",
         acknowledgeButtonText = "Ok",
      ),
      onDismissRequest = {},
   )
}