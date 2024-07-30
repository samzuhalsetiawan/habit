package com.wahyusembiring.habit.core.presentation.composable.modalbottomsheet.picksubject

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import com.wahyusembiring.habit.core.domain.model.Attachment
import com.wahyusembiring.habit.feature.subject.domain.model.Subject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
class PickSubjectModalBottomSheetState(
   private val coroutineScope: CoroutineScope,
   val materialSheetState: SheetState
) {
   var subjects: List<Subject> by mutableStateOf(emptyList())
      private set

   fun updateSubject(vararg subject: Subject) {
      subjects = subject.toList()
   }

   fun hideBottomSheet(
      onDismissRequest: () -> Unit
   ) {
      coroutineScope.launch { materialSheetState.hide() }.invokeOnCompletion { onDismissRequest() }
   }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun rememberPickSubjectModalBottomSheetState(
   skipPartiallyExpanded: Boolean = false,
   coroutineScope: CoroutineScope = rememberCoroutineScope(),
   materialSheetState: SheetState = rememberModalBottomSheetState(skipPartiallyExpanded)
): PickSubjectModalBottomSheetState {
   return remember {
      PickSubjectModalBottomSheetState(coroutineScope, materialSheetState)
   }
}