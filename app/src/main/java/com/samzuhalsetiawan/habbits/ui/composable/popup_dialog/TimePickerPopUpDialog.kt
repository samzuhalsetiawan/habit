package com.samzuhalsetiawan.habbits.ui.composable.popup_dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.samzuhalsetiawan.habbits.ui.composable.daily_reminder_button.DailyReminderButton
import com.samzuhalsetiawan.habbits.ui.localcomposition.provider.PopUpDialog
import com.samzuhalsetiawan.habbits.ui.localcomposition.provider.PopUpDialogResponse
import com.samzuhalsetiawan.habbits.ui.localcomposition.provider.PopUpDialogResult
import com.samzuhalsetiawan.habbits.ui.composable.daily_reminder_button.DailyReminderButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimePickerPopUpDialog(
   dialog: PopUpDialog.BasicTimePicker,
   onDismissRequest: () -> Unit,
   modifier: Modifier = Modifier
) {
   BasicAlertDialog(
      modifier = modifier
         .background(
            color = AlertDialogDefaults.containerColor,
            shape = RoundedCornerShape(28.dp)
         ),
      onDismissRequest = {
         dialog.callback.callback(PopUpDialogResult(PopUpDialogResponse.DISMISS))
         onDismissRequest()
      }
   ) {
      val timePickerState = rememberTimePickerState()

      Column(
         modifier = Modifier.padding(24.dp)
      ) {
         Text(
            modifier = Modifier.padding(bottom = 16.dp),
            text = dialog.title,
            style = MaterialTheme.typography.headlineSmall
         )
         TimePicker(state = timePickerState)
         Row(
            modifier = Modifier
               .fillMaxWidth()
               .padding(top = 24.dp),
            horizontalArrangement = Arrangement.End
         ) {
            TextButton(onClick = {
               dialog.callback.callback(PopUpDialogResult(PopUpDialogResponse.NEGATIVE))
               onDismissRequest()
            }) {
               Text(text = dialog.negaviteButtonText)
            }
            TextButton(onClick = {
               dialog.callback.callback(
                  PopUpDialogResult(
                     response = PopUpDialogResponse.POSITIVE,
                     data = PopUpDialog.BasicTimePicker.Result(
                        hour = timePickerState.hour,
                        minute = timePickerState.minute
                     )
                  )
               )
               onDismissRequest()
            }) {
               Text(text = dialog.positiveButtonText)
            }
         }
      }
   }
}

@Preview(showBackground = true)
@Composable
private fun TimePickerPopUpDialogPreview() {
   MaterialTheme {
      Surface(
         modifier = Modifier.fillMaxSize()
      ) {
         Box(
            contentAlignment = Alignment.Center
         ) {
            TimePickerPopUpDialog(
               dialog = PopUpDialog.BasicTimePicker(
                  title = "Pilih Waktu",
                  positiveButtonText = "Ok",
                  negaviteButtonText = "Cancel",
                  callback = {}
               ),
               onDismissRequest = { }
            )
         }
      }
   }
}