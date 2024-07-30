package com.wahyusembiring.habit.core.presentation.composable.textinput

import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter

@Composable
fun TextInputWithLeadingIcon(
   modifier: Modifier = Modifier,
   icon: Painter,
   iconDescription: String,
   value: String,
   onValueChange: (String) -> Unit
) {
   ListItem(
      modifier = modifier,
      leadingContent = {
         Icon(
            painter = icon,
            contentDescription = iconDescription
         )
      },
      headlineContent = {
         BasicTextField(
            value = value,
            onValueChange = onValueChange,
            singleLine = true
         )
      }
   )
}