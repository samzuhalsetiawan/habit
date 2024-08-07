package com.wahyusembiring.habit.core.presentation.composable.modalbottomsheet.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.wahyusembiring.habit.R
import com.wahyusembiring.habit.core.domain.constant.DateAndTimePattern
import com.wahyusembiring.habit.core.domain.util.ConvertionUtil.formatToString
import java.util.Date


@Composable
fun AddDateButton(
   modifier: Modifier = Modifier,
   date: Date?,
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
            painter = painterResource(id = R.drawable.ic_date_picker),
            contentDescription = stringResource(R.string.pick_date)
         )
      },
      headlineContent = {
         if (date != null) {
            Column {
               Text(text = date.formatToString(DateAndTimePattern.SIMPLE_DATE))
            }
         } else {
            Text(
               color = TextFieldDefaults.colors().disabledTextColor,
               text = stringResource(R.string.add_date),
            )
         }
      }
   )
}

@Preview(showBackground = true)
@Composable
private fun AddDatePreview() {
   MaterialTheme {
      Surface {
         Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
         ) {
            val date = Date(System.currentTimeMillis())
            AddDateButton(date = date)
         }
      }
   }
}