package com.wahyusembiring.habit.presentation.handler.floatingactionbutton

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import com.wahyusembiring.habit.presentation.composable.fab.ClickedFAB
import com.wahyusembiring.habit.presentation.composable.modalbottomsheet.ModalBottomSheet
import com.wahyusembiring.habit.presentation.composable.modalbottomsheet.ModalBottomSheetState
import com.wahyusembiring.habit.presentation.composable.modalbottomsheet.ModalBottomSheetUserAction

@Composable
fun FloatingActionButtonClickHandler(
   clickedFABState: MutableState<ClickedFAB?>
) {
   when (clickedFABState.value) {
      ClickedFAB.REMINDER -> Unit
      ClickedFAB.EXAM -> Unit
      ClickedFAB.HOMEWORK -> OnHomeworkFABClick(clickedFABState)
      else -> Unit
   }
}

@Composable
private fun OnHomeworkFABClick(
   clickedFABState: MutableState<ClickedFAB?>
) {
   val modalBottomSheetState by remember {
      mutableStateOf(ModalBottomSheetState())
   }
   val userAction = object : ModalBottomSheetUserAction {
      override fun onHomeworkTitleChanged(title: String) {

      }

      override fun onModalBottomSheetDismissRequest() {
         clickedFABState.value = null
      }

      override fun onSaveButtonPressed() {

      }
   }
   ModalBottomSheet(
      modalBottomSheetState = modalBottomSheetState,
      userAction = userAction
   )
}