package com.samzuhalsetiawan.habbits.ui.composable.inputfield

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.samzuhalsetiawan.habbits.models.Habit

@Composable
fun DropdownTextField(
   onSetHabitType: (Habit.Types) -> Unit,
   value: String,
   modifier: Modifier = Modifier,
) {

   var dropdownMenuExpanded by remember { mutableStateOf(false) }

   Box(
      modifier = modifier
         .fillMaxWidth()
         .wrapContentSize(Alignment.TopEnd)
   ) {
      OutlinedTextField(
         modifier = Modifier.fillMaxWidth(),
         value = value,
         enabled = false,
         onValueChange = {  },
         colors = OutlinedTextFieldDefaults.colors().copy(
            disabledTextColor = TextFieldDefaults.colors().unfocusedTextColor,
            disabledSupportingTextColor = TextFieldDefaults.colors().unfocusedSupportingTextColor,
            disabledTrailingIconColor = TextFieldDefaults.colors().unfocusedTrailingIconColor,
            disabledIndicatorColor = TextFieldDefaults.colors().unfocusedIndicatorColor
         ),
         trailingIcon = {
            IconButton(onClick = {
               dropdownMenuExpanded = true
            }) {
               Icon(
                  imageVector = Icons.Default.ArrowDropDown,
                  contentDescription = "Tipe Habit"
               )
            }
         },
         supportingText = {
            Text(text = "Tipe dari habit ini")
         }
      )
      DropdownMenu(
         expanded = dropdownMenuExpanded,
         onDismissRequest = { dropdownMenuExpanded = false }
      ) {
         DropdownMenuItem(
            text = { Text(text = Habit.Types.HEALTH.displayName) },
            onClick = {
               onSetHabitType(Habit.Types.HEALTH)
               dropdownMenuExpanded = false
            }
         )
         DropdownMenuItem(
            text = { Text(text = Habit.Types.PRODUCTIVITY.displayName) },
            onClick = {
               onSetHabitType(Habit.Types.PRODUCTIVITY)
               dropdownMenuExpanded = false
            }
         )
         DropdownMenuItem(
            text = { Text(text = Habit.Types.SELF_DEVELOPMENT.displayName) },
            onClick = {
               onSetHabitType(Habit.Types.SELF_DEVELOPMENT)
               dropdownMenuExpanded = false
            }
         )
      }
   }
}