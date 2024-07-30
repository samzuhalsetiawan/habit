package com.wahyusembiring.habit.core.presentation.composable.picker.timepicker

import androidx.compose.foundation.background
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
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.wahyusembiring.habit.R
import com.wahyusembiring.habit.core.domain.model.Time
import com.wahyusembiring.habit.core.domain.util.GetterUtil
import com.wahyusembiring.habit.theme.spacing
import java.util.Calendar
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimePicker(
   modifier: Modifier = Modifier,
   onTimeSelected: (time: Time) -> Unit,
   onDismissRequest: () -> Unit,
) {
   val currentDateTime = remember { GetterUtil.getCurrentDateTime() }
   val timePickerState = rememberTimePickerState(
      initialHour = currentDateTime.get(Calendar.HOUR_OF_DAY),
      initialMinute = currentDateTime.get(Calendar.MINUTE)
   )

   Dialog(onDismissRequest = onDismissRequest) {
      Surface(
         modifier = modifier,
         color = AlertDialogDefaults.containerColor,
         shape = AlertDialogDefaults.shape,
         tonalElevation = AlertDialogDefaults.TonalElevation
      ) {
         Column(
            modifier = Modifier.padding(vertical = MaterialTheme.spacing.Large),
            horizontalAlignment = Alignment.CenterHorizontally,
         ) {
            Text(
               modifier = Modifier
                  .padding(horizontal = MaterialTheme.spacing.Large)
                  .padding(bottom = MaterialTheme.spacing.Medium),
               color = MaterialTheme.colorScheme.primary,
               text = stringResource(R.string.when_do_you_want_to_be_reminded)
            )
            androidx.compose.material3.TimePicker(state = timePickerState)
            Row(
               modifier = Modifier
                  .fillMaxWidth()
                  .padding(horizontal = MaterialTheme.spacing.Large),
               horizontalArrangement = Arrangement.End
            ) {
               TextButton(onClick = onDismissRequest) {
                  Text(text = stringResource(id = R.string.cancel))
               }
               TextButton(
                  onClick = {
                     val time = Time(timePickerState.hour, timePickerState.minute)
                     onTimeSelected(time)
                     onDismissRequest()
                  }
               ) {
                  Text(text = stringResource(id = R.string.confirm))
               }
            }
         }
      }
   }
}

@Preview(showBackground = true)
@Composable
private fun TimePickerPreview() {
   MaterialTheme {
      Box(
         modifier = Modifier.fillMaxSize(),
         contentAlignment = Alignment.Center
      ) {
         TimePicker(
            onTimeSelected = {},
            onDismissRequest = {},
         )
      }
   }
}