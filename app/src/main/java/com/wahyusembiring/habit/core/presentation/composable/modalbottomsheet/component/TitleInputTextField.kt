package com.wahyusembiring.habit.core.presentation.composable.modalbottomsheet.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.wahyusembiring.habit.R

@Composable
fun TitleInputTextField(
   label: String,
   title: String,
   onTitleChange: (String) -> Unit
) {
   OutlinedTextField(
      modifier = Modifier.fillMaxWidth(),
      label = {
         Text(text = label)
      },
      leadingIcon = {
         Icon(
            painter = painterResource(id = R.drawable.ic_homework),
            contentDescription = label
         )
      },
      singleLine = true,
      value = title,
      onValueChange = onTitleChange,
   )
}