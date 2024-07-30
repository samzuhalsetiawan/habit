package com.wahyusembiring.habit.core.presentation.composable.modalbottomsheet.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.wahyusembiring.habit.R
import com.wahyusembiring.habit.theme.spacing

@Composable
fun CloseAndSaveHeader(
   modifier: Modifier = Modifier,
   onCloseButtonClicked: () -> Unit,
   onSaveButtonClicked: () -> Unit,
   closeButtonDescription: String,
) {
   Row(
      modifier = modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically
   ) {
      IconButton(onClick = onCloseButtonClicked) {
         Icon(
            painter = painterResource(id = R.drawable.ic_close),
            contentDescription = closeButtonDescription
         )
      }
      Button(
         modifier = Modifier.padding(end = MaterialTheme.spacing.Medium),
         onClick = onSaveButtonClicked
      ) {
         Text(text = stringResource(R.string.save))
      }
   }
}