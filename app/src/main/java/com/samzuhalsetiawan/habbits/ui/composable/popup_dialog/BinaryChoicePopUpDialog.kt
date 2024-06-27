package com.samzuhalsetiawan.habbits.ui.composable.popup_dialog

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.samzuhalsetiawan.habbits.R
import com.samzuhalsetiawan.habbits.ui.localcomposition.provider.PopUpDialog
import com.samzuhalsetiawan.habbits.ui.localcomposition.provider.PopUpDialogResponse

@Composable
fun BinaryChoicePopUpDialog(
   dialog: PopUpDialog.BinaryChoice,
   onDismissRequest: () -> Unit,
   modifier: Modifier = Modifier
) {
   AlertDialog(
      modifier = modifier,
      icon = {
         Icon(
            painter = painterResource(id = R.drawable.ic_question_mark),
            contentDescription = "Question Mark Icon",
            tint = MaterialTheme.colorScheme.primary
         )
      },
      title = {
         Text(text = dialog.title)
      },
      text = {
         Text(
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            text = dialog.message
         )
      },
      onDismissRequest = {
         dialog.callback.onResponse(PopUpDialogResponse.DISMISS)
         onDismissRequest()
      },
      confirmButton = {
         TextButton(onClick = {
            dialog.callback.onResponse(PopUpDialogResponse.POSITIVE)
            onDismissRequest()
         }) {
            Text(text = dialog.positiveButtonText)
         }
      },
      dismissButton = {
         TextButton(onClick = {
            dialog.callback.onResponse(PopUpDialogResponse.NEGATIVE)
            onDismissRequest()
         }) {
            Text(text = dialog.negaviteButtonText)
         }
      }
   )
}

@Preview(showBackground = true)
@Composable
private fun BinaryChoicePopUpDialogPreview() {
   BinaryChoicePopUpDialog(
      dialog = PopUpDialog.BinaryChoice(
         title = "Title",
         message = "Message",
         positiveButtonText = "Positive",
         negaviteButtonText = "Negative",
         callback = { }
      ),
      onDismissRequest = {}
   )

}