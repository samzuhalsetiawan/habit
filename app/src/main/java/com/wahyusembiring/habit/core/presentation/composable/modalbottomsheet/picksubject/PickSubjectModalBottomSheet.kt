package com.wahyusembiring.habit.core.presentation.composable.modalbottomsheet.picksubject

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.wahyusembiring.habit.R
import com.wahyusembiring.habit.core.presentation.composable.modalbottomsheet.component.AddNewSubject
import com.wahyusembiring.habit.core.presentation.composable.modalbottomsheet.component.SubjectListItem
import com.wahyusembiring.habit.feature.subject.domain.model.Subject

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PickSubjectModalBottomSheet(
   modifier: Modifier = Modifier,
   sheetState: SheetState,
   onDismissRequest: () -> Unit,
   subjects: List<Subject>,
   onSubjectSelected: (subject: Subject) -> Unit,
   navigateToCreateSubjectScreen: () -> Unit,
) {
   ModalBottomSheet(
      modifier = modifier,
      onDismissRequest = onDismissRequest,
      sheetState = sheetState,
   ) {
      PickSubjectModalBottomSheetContent(
         onSubjectSelected = onSubjectSelected,
         onCancelButtonClicked = onDismissRequest,
         navigateToCreateSubjectScreen = navigateToCreateSubjectScreen,
         subjects = subjects
      )
   }

}

@Composable
private fun ColumnScope.PickSubjectModalBottomSheetContent(
   onSubjectSelected: (subject: Subject) -> Unit,
   onCancelButtonClicked: () -> Unit,
   navigateToCreateSubjectScreen: () -> Unit,
   subjects: List<Subject> = emptyList()
) {
   Text(text = stringResource(R.string.pick_a_subject))

   AddNewSubject(
      onClicked = navigateToCreateSubjectScreen
   )
   subjects.forEach { subject ->
      SubjectListItem(
         subject = subject,
         onClicked = onSubjectSelected
      )
   }
   TextButton(
      modifier = Modifier.align(Alignment.End),
      onClick = onCancelButtonClicked
   ) {
      Text(text = stringResource(id = R.string.cancel))
   }
}