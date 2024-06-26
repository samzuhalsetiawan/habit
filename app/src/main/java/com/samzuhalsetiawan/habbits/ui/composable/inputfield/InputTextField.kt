package com.samzuhalsetiawan.habbits.ui.composable.inputfield

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType

@Composable
fun InputTextField(
   value: String,
   onValueChange: (String) -> Unit,
   modifier: Modifier = Modifier
) {

   val focusManager = LocalFocusManager.current

   OutlinedTextField(
      modifier = modifier,
      value = value,
      onValueChange = onValueChange,
      label = {
         Text(text = "Nama Habit")
      },
      supportingText = {
         Text(text = "Nama habit yang ingin saya bentuk")
      },
      maxLines = 1,
      singleLine = true,
      keyboardOptions = KeyboardOptions.Default.copy(
         keyboardType = KeyboardType.Text,
         showKeyboardOnFocus = true,
         imeAction = ImeAction.Done
      ),
      keyboardActions = KeyboardActions(onDone = {
         focusManager.clearFocus()
      })
   )
}