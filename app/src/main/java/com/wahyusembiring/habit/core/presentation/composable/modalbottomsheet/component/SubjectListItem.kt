package com.wahyusembiring.habit.core.presentation.composable.modalbottomsheet.component

import androidx.compose.foundation.clickable
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.wahyusembiring.habit.R
import com.wahyusembiring.habit.feature.subject.domain.model.Subject

@Composable
fun SubjectListItem(
   modifier: Modifier = Modifier,
   subject: Subject,
   onClicked: ((subject: Subject) -> Unit)? = null
) {
   ListItem(
      modifier = modifier
         .then(
            if (onClicked != null) {
               Modifier.clickable { onClicked(subject) }
            } else {
               Modifier
            }
         ),
      leadingContent = {
         Icon(
            painter = painterResource(id = R.drawable.ic_subjects),
            contentDescription = subject.name,
            tint = subject.color
         )
      },
      headlineContent = {
         Text(text = subject.name)
      },
      trailingContent = {
         Icon(
            painter = painterResource(id = R.drawable.ic_more_vertical),
            contentDescription = stringResource(R.string.more_option)
         )
      }
   )
}

@Composable
fun AddNewSubject(
   modifier: Modifier = Modifier,
   onClicked: (() -> Unit)? = null
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
            painter = painterResource(id = R.drawable.ic_add),
            contentDescription = stringResource(R.string.add_new_subject),
            tint = MaterialTheme.colorScheme.primary
         )
      },
      headlineContent = {
         Text(text = stringResource(R.string.add_new_subject))
      }
   )
}