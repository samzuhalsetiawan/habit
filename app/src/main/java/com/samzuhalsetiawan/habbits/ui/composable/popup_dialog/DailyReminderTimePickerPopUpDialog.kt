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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.samzuhalsetiawan.habbits.ui.composable.daily_reminder_button.DailyReminderButton
import com.samzuhalsetiawan.habbits.ui.localcomposition.provider.PopUpDialog
import com.samzuhalsetiawan.habbits.ui.localcomposition.provider.PopUpDialogResponse
import com.samzuhalsetiawan.habbits.ui.localcomposition.provider.PopUpDialogResult
import com.samzuhalsetiawan.habbits.ui.composable.daily_reminder_button.DailyReminderButton
import java.time.DayOfWeek

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DailyReminderTimePickerPopUpDialog(
   dialog: PopUpDialog.DailyReminderTimePicker,
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
      val selectedDays = remember { mutableStateListOf<DayOfWeek>() }

      LaunchedEffect(selectedDays) {
         println(selectedDays)
      }

      Column(
         modifier = Modifier.padding(24.dp)
      ) {
         Text(
            modifier = Modifier.padding(bottom = 16.dp),
            text = dialog.title,
            style = MaterialTheme.typography.headlineSmall
         )
         DailyReminderButton(
            selectedDays = selectedDays,
            modifier = Modifier.padding(bottom = 16.dp)
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
                     data = PopUpDialog.DailyReminderTimePicker.Result(
                        hour = timePickerState.hour,
                        minute = timePickerState.minute,
                        days = selectedDays
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
private fun DailyReminderTimePickerPopUpDialogPreview() {
   MaterialTheme {
      Surface(
         modifier = Modifier.fillMaxSize()
      ) {
         Box(
            contentAlignment = Alignment.Center
         ) {
            DailyReminderTimePickerPopUpDialog(
               dialog = PopUpDialog.DailyReminderTimePicker(
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