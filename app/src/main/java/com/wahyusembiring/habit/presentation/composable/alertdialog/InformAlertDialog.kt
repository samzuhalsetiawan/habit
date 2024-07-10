package com.wahyusembiring.habit.presentation.composable.alertdialog

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.wahyusembiring.habit.presentation.theme.spacing

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InformAlertDialog(
   dialog: AlertDialog.Inform,
   onDismissRequest: () -> Unit,
) {
   BasicAlertDialog(
      onDismissRequest = onDismissRequest
   ) {
      Surface(
         color = AlertDialogDefaults.containerColor,
         shape = AlertDialogDefaults.shape,
         tonalElevation = AlertDialogDefaults.TonalElevation,
      ) {
         Column(
            modifier = Modifier
               .fillMaxWidth()
               .padding(MaterialTheme.spacing.Large),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top
         ) {
            Text(
               modifier = Modifier
                  .padding(bottom = MaterialTheme.spacing.Medium),
               style = MaterialTheme.typography.headlineSmall,
               color = AlertDialogDefaults.titleContentColor,
               text = dialog.title
            )
            Text(
               modifier = Modifier
                  .fillMaxWidth(),
               style = MaterialTheme.typography.bodyMedium,
               color = AlertDialogDefaults.textContentColor,
               text = dialog.message
            )
            Row(
               modifier = Modifier
                  .fillMaxWidth()
                  .padding(top = MaterialTheme.spacing.Large),
               horizontalArrangement = Arrangement.End,
               verticalAlignment = Alignment.CenterVertically
            ) {
               TextButton(onClick = dialog.onButtonClicked) {
                  Text(text = dialog.buttonText)
               }
            }
         }
      }
   }
}

@Preview(showBackground = true)
@Composable
private fun ConfirmAlertDialogPreview() {
   Surface {
      Box(
         modifier = Modifier.fillMaxSize(),
         contentAlignment = Alignment.Center
      ) {
         InformAlertDialog(
            onDismissRequest = { },
            dialog = AlertDialog.Inform(
               title = "Title",
               message = "Message",
               buttonText = "Ok",
               onButtonClicked = { /*TODO*/ }
            )
         )
      }
   }
}