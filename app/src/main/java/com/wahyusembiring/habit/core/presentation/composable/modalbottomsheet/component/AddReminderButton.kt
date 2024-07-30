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
import com.wahyusembiring.habit.core.domain.constant.DateAndTimePattern
import com.wahyusembiring.habit.core.domain.model.Time
import com.wahyusembiring.habit.core.domain.util.ConvertionUtil.formatToString
import java.util.Date

@Composable
fun AddReminderButton(
   modifier: Modifier = Modifier,
   onClicked: (() -> Unit)? = null,
   time: Time?
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
            painter = painterResource(id = R.drawable.ic_reminder),
            contentDescription = stringResource(R.string.add_reminder)
         )
      },
      headlineContent = {
         if (time != null) {
            Column {
               Text(
                  text = "${stringResource(R.string.remind_me_at)} ${time.hour}:${time.minute}"
               )
            }
         } else {
            Text(
               color = TextFieldDefaults.colors().disabledTextColor,
               text = stringResource(id = R.string.add_reminder),
            )
         }
      }
   )
}