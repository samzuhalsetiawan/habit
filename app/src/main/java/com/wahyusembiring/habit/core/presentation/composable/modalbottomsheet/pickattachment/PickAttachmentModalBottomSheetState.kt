package com.wahyusembiring.habit.core.presentation.composable.modalbottomsheet.pickattachment

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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
class PickAttachmentModalBottomSheetState(
   private val coroutineScope: CoroutineScope,
   val materialSheetState: SheetState
) {
   var attachments: List<Attachment> by mutableStateOf(emptyList())
      private set

   fun addAttachment(vararg attachment: Attachment) {
      attachments = (attachments + attachment).distinctBy { it.uri.toString() }
   }

   fun hideBottomSheet(
      onDismissRequest: () -> Unit
   ) {
      coroutineScope.launch { materialSheetState.hide() }.invokeOnCompletion { onDismissRequest() }
   }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun rememberPickAttachmentModalBottomSheetState(
   skipPartiallyExpanded: Boolean = false,
   coroutineScope: CoroutineScope = rememberCoroutineScope(),
   materialSheetState: SheetState = rememberModalBottomSheetState(skipPartiallyExpanded)
): PickAttachmentModalBottomSheetState {
   return remember {
      PickAttachmentModalBottomSheetState(coroutineScope, materialSheetState)
   }
}