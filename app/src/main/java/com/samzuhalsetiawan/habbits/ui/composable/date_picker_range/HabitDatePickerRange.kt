package com.samzuhalsetiawan.habbits.ui.composable.date_picker_range

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DateRangePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDateRangePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HabitDatePickerRange(
   modifier: Modifier = Modifier,
   onSave: (range: LongRange) -> Unit,
   onDismiss: () -> Unit
) {

   val state = rememberDateRangePickerState()

   BasicAlertDialog(
      onDismissRequest = onDismiss,
      properties = DialogProperties(
         usePlatformDefaultWidth = false
      ),
      modifier = modifier.fillMaxSize()
   ) {
      Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Top) {
         // Add a row with "Save" and dismiss actions.
         Row(
            modifier = Modifier
               .fillMaxWidth()
               .background(DatePickerDefaults.colors().containerColor)
               .padding(start = 12.dp, end = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
         ) {
            IconButton(onClick = onDismiss) {
               Icon(Icons.Filled.Close, contentDescription = "Localized description")
            }
            TextButton(
               onClick = {
                  val range = state.selectedStartDateMillis!!..state.selectedEndDateMillis!!
                  onSave(range)
                  onDismiss()
               },
               enabled = state.selectedEndDateMillis != null
            ) {
               Text(text = "Save")
            }
         }
         DateRangePicker(
            state = state,
            modifier = Modifier
               .weight(1f)
               .background(DatePickerDefaults.colors().containerColor)
         )
      }
   }
}