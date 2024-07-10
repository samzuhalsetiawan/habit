package com.wahyusembiring.habit.presentation.composable.modalbottomsheet

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.wahyusembiring.habit.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ModalBottomSheet(
   modifier: Modifier = Modifier,
   modalBottomSheetState: ModalBottomSheetState,
   userAction: ModalBottomSheetUserAction
) {
   ModalBottomSheet(
      modifier = modifier,
      onDismissRequest = userAction::onModalBottomSheetDismissRequest
   ) {
      Row(
         modifier = Modifier.fillMaxWidth(),
         horizontalArrangement = Arrangement.SpaceBetween
      ) {
         Icon(
            painter = painterResource(id = R.drawable.ic_close),
            contentDescription = stringResource(R.string.close_add_homework_sheet)
         )
         Button(onClick = userAction::onSaveButtonPressed) {
            Text(text = stringResource(R.string.save))
         }
      }
      OutlinedTextField(
         modifier = Modifier.fillMaxWidth(),
         label = {
            Text(text = stringResource(R.string.homework_title))
         },
         leadingIcon = {
            Icon(
               painter = painterResource(id = R.drawable.ic_homework),
               contentDescription = stringResource(id = R.string.homework_title)
            )
         },
         singleLine = true,
         value = modalBottomSheetState.homeworkTitle,
         onValueChange = userAction::onHomeworkTitleChanged,
      )
   }
}

data class ModalBottomSheetState(
   val homeworkTitle: String = "",
)

interface ModalBottomSheetUserAction {
   fun onHomeworkTitleChanged(title: String)
   fun onModalBottomSheetDismissRequest()
   fun onSaveButtonPressed()
}