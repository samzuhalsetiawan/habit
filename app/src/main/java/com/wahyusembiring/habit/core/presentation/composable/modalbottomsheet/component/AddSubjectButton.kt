package com.wahyusembiring.habit.core.presentation.composable.modalbottomsheet.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.wahyusembiring.habit.R
import com.wahyusembiring.habit.feature.subject.domain.model.Subject

@Composable
fun AddSubjectButton(
   modifier: Modifier = Modifier,
   subject: Subject?,
   onClicked: (() -> Unit)? = null,
) {
   ListItem(
      modifier = modifier
         .then(
            if (onClicked != null) {
               Modifier.clickable { onClicked() }
            } else {
               Modifier
            }
         ),
      leadingContent = {
         Icon(
            painter = painterResource(id = R.drawable.ic_subjects),
            contentDescription = stringResource(R.string.add_subject)
         )
      },
      headlineContent = {
         if (subject != null) {
            Column {
               Text(text = subject.name)
            }
         } else {
            Text(
               color = TextFieldDefaults.colors().disabledTextColor,
               text = stringResource(R.string.add_subject),
            )
         }
      }
   )
}