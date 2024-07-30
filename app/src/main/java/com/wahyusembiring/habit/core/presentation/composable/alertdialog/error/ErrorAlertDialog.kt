package com.wahyusembiring.habit.core.presentation.composable.alertdialog.error

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.wahyusembiring.habit.R
import com.wahyusembiring.habit.theme.spacing

@Composable
fun ErrorAlertDialog(
   message: String,
   buttonText: String,
   onButtonClicked: () -> Unit,
   onDismissRequest: () -> Unit,
) {
   AlertDialog(
      onDismissRequest = onDismissRequest,
      confirmButton = {
         TextButton(onClick = onButtonClicked) {
            Text(text = buttonText)
         }
      },
      icon = {
         Icon(
            painter = painterResource(R.drawable.ic_error),
            contentDescription = stringResource(R.string.error),
            tint = MaterialTheme.colorScheme.error
         )
      },
      title = {
         Text(
            modifier = Modifier
               .padding(bottom = MaterialTheme.spacing.Medium),
            style = MaterialTheme.typography.headlineSmall,
            color = AlertDialogDefaults.titleContentColor,
            text = stringResource(R.string.error)
         )
      },
      text = {
         Text(
            modifier = Modifier
               .fillMaxWidth(),
            style = MaterialTheme.typography.bodyMedium,
            color = AlertDialogDefaults.textContentColor,
            text = message
         )
      }
   )
}